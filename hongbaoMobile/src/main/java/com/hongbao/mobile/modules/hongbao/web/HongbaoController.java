package com.hongbao.mobile.modules.hongbao.web;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hongbao.mobile.common.config.ResultCodeConstants;
import com.hongbao.mobile.common.exception.HongbaoException;
import com.hongbao.mobile.common.lock.KeyLock;
import com.hongbao.mobile.common.lock.LockUtils;
import com.hongbao.mobile.common.utils.JedisUtils;
import com.hongbao.mobile.common.web.BaseController;
import com.hongbao.mobile.modules.hongbao.entity.HongbaoInfo;
import com.hongbao.mobile.modules.hongbao.service.HongbaoInfoService;
import com.hongbao.mobile.modules.pay.service.PayInfoService;
import com.hongbao.mobile.modules.user.entity.UserInfo;
import com.hongbao.mobile.modules.user.service.UserInfoService;
import com.hongbao.mobile.modules.yongjin.entity.DuihuanInfo;
import com.hongbao.mobile.modules.yongjin.service.DuihuanInfoService;

/**
 * 红包Controller
 * @ClassName HongbaoController
 * @Description 
 */
@Controller
@RequestMapping(value = "/hongbao")
public class HongbaoController extends BaseController {
	
	/**
	 * 红包信息Service
	 */
	@Autowired
	private HongbaoInfoService hongbaoInfoService;
	
	/**
	 * 支付信息Service
	 */
	@Autowired
	private PayInfoService payInfoService;
	
	/**
	 * 用户信息service
	 */
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 生成支付信息
	 * @Title makePayInfo
	 * @Description 
	 * @param amount
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "makePayInfo", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String makePayInfo(String amountType,Integer amount,HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		try {
			//生成支付信息
			HongbaoInfo hongbaoInfo = hongbaoInfoService.makePayInfo(amountType,amount,"1");
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//设置支付地址
			returnJson.put("payUrl", hongbaoInfo.getPayUrl());
			
		} catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		} catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		return returnJson.toString();
	}
	
//	@RequestMapping(value = "payTest")
//	public String payTest(String hongbaoNo,HttpServletRequest request,Model model) {
//		try {
//			//查询订单信息
//			HongbaoInfo hongbaoInfo = hongbaoInfoService.getByHongbaoNo(hongbaoNo);
//			if(hongbaoInfo==null) {
//				throw new HongbaoException(ResultCodeConstants.C0012,"订单不存在");
//			}
//			//判断是待支付的情况
//			if(hongbaoInfo.getPayFlag().equals("0")) {
//				String tradeNo = IdGen.uuid();
//				//完成支付订单信息
//				hongbaoInfo = payInfoService.finishPayOrder(hongbaoNo,tradeNo);
//				//设置支付订单map数据
//				PayUtil.setPayHongbaoInfoMapData(hongbaoInfo.getHongbaoNo(), hongbaoInfo);
//			}
//			
//		} catch (HongbaoException duobao) {
//			logger.error(duobao.getResultCode().getMsg());
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//		return "redirect:/index";
//	}
	
	/**
	 * 打开红包
	 * @Title openHongbao
	 * @Description 
	 * @param hongbaoId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "openHongbao", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String openHongbao(String hongbaoId,HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		
		//获取session中的用户信息
		UserInfo userInfoSession = (UserInfo)request.getSession().getAttribute("userInfo");
		//获取用户操作锁
		KeyLock<String> userOperationLock = LockUtils.getUserOperationLock();
		//锁住用户id
		userOperationLock.lock(userInfoSession.getId());
		
		try {
			//打开红包
			HongbaoInfo hongbaoInfo = hongbaoInfoService.openHongbao(hongbaoId);
			//返回成功信息
			returnJson = ResultCodeConstants.C0.toJsonObject();
			//设置幸运金额
			returnJson.put("luckyAmount", hongbaoInfo.getLuckyAmount());
			
		} 
		catch (HongbaoException hongbao) {
			//捕获业务异常
			return hongbao.getResultJson();
		} 
		catch (Exception e) {
			e.printStackTrace();
			//1005:系统异常
			return ResultCodeConstants.C1005.toString();
		}
		finally {
			//操作完成解锁
			userOperationLock.unlock(userInfoSession.getId());
		}
		
		return returnJson.toString();
	}
	
	
	/**************************************我的红包记录   start********************************************/
	/**
	 * 分页显示
	 */
	private final static int pageSize = 8;   //测试2条。。正8条
	
	/**
	 * 我的红包记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "notes")
	public String mynotes(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		
		//查询结果从第0条开始，查询条数记录
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("start", 0);
		params.put("limit", pageSize);
		params.put("userid", userInfo.getId());
		List<HongbaoInfo> list = hongbaoInfoService.getHongBaoListByUser(params);
		model.addAttribute("pageList", list);
		model.addAttribute("pageCount", list.size());
		
		return "modules/hongbao/notes";
	}
	
	/**
	 * 我的红包记录ajax请求
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "notes_ajax",method = RequestMethod.POST)
	@ResponseBody
	public String notes_ajax(HttpServletRequest request, HttpServletResponse response,String page,Model model) {
		JSONObject object = new JSONObject();
		try {
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			//设置用户编号
			model.addAttribute("userNo", userInfo.getUserNo());
			
			int pageNumber = 2;
			try { pageNumber = Integer.parseInt(page); } catch (Exception e) { e.printStackTrace();	}
			
			//查询结果从第0条开始，查询2条记录
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("start", pageSize * (pageNumber - 1));
			params.put("limit", pageSize );
			params.put("userid", userInfo.getId());
			List<HongbaoInfo> list = hongbaoInfoService.getHongBaoListByUser(params);
			object.put("status", 0);
			object.put("data", list);
			object.put("count", list.size());
			object.put("pageSize", pageSize);
			object.put("page", pageNumber + 1);
		} catch (Exception e) {
			object.put("status", 1);
			object.put("msg", "系统异常");
		}
		return object.toString();
	}
	
	/**************************************我的红包记录   end********************************************/
	
	/**
	 * 代理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "acting")
	public String acting(HttpServletRequest request, HttpServletResponse response,Model model) {
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		return "modules/hongbao/acting";
	}
	
	/**
	 * 佣金说明
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "shuoming")
	public String shuoming(HttpServletRequest request, HttpServletResponse response,Model model) {
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		return "modules/hongbao/shuoming";
	}
	
	/**********************************佣金兑换   start***************************************/
	
	/**
	 * 佣金兑换记录service
	 */
	@Autowired
	private DuihuanInfoService duihuanInfoService;
	
	/**
	 * 佣金兑换
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "yongjin")
	public String yongjin(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		//获取最新的用户信息
		UserInfo user_new = userInfoService.get(userInfo.getId());
		model.addAttribute("amountSum", user_new.getBalance());
		
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		
		//查询结果从第0条开始，查询条数记录
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userInfo.getId());
		params.put("transferType", "1");
		List<DuihuanInfo> list = duihuanInfoService.getDuihuanListByUser(params);
		model.addAttribute("pageList", list);
		model.addAttribute("pageCount", list.size());
		
		return "modules/hongbao/yongjin";
	}
	
	/**********************************佣金兑换   end***************************************/
	
	/**
	 * 用户协议
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "agreement")
	public String userAgreements(HttpServletRequest request, HttpServletResponse response,Model model) {
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		return "modules/hongbao/agreement";
	}
	
	
	/**
	 * 投诉
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tousu")
	public String tousu(HttpServletRequest request, HttpServletResponse response,Model model) {
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		return "modules/hongbao/tousu";
	}
	
	
	/**
	 * 客服
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "kefu")
	public String kefu(HttpServletRequest request, HttpServletResponse response,Model model) {
		//查询中奖信息
		String luckyUserListStr = JedisUtils.get(JedisUtils.KEY_PREFIX+":luckyUserList");
		if(StringUtils.isNotBlank(luckyUserListStr)) {
			JSONArray luckyUserList = JSONArray.fromObject(luckyUserListStr);
			model.addAttribute("luckyUserList", luckyUserList);
		}
		//获取登录用户
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
		//设置用户编号
		model.addAttribute("userNo", userInfo.getUserNo());
		return "modules/hongbao/kefu";
	}
	
	/**
	 * 生成二维码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/userCode")
    public void watermark(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String context_path = request.getSession().getServletContext().getRealPath("");
			//获取登录用户
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");
			//设置用户编号
			String userNo = userInfo.getUserNo();
			String parentUserid = userInfo.getId();
			//先删除
			String path_new  = context_path +"/codepic/user_"+userNo+".jpg";
			File file_old = new File(path_new);
			if(file_old.exists()) {
				file_old.delete();
			}
			
			//生成前缀
			String prefix = new Random().nextInt(100)+100+"";
			//在redis中随机取一个域名
			String yuming  = JedisUtils.get(JedisUtils.KEY_PREFIX+":yuming");
			String[] yumings = yuming.split(",");
			//获取域名次数
			int yumingNum = yumings.length;
			//生成域名数组下标
			int yumingIndex = new Random().nextInt(yumingNum);
			//获取域名
			String ming = yumings[yumingIndex];
			String url = "http://"+prefix+"."+ming+"/anq?channel=0&parentUserid="+parentUserid;
			
			//二维码
			String content = url;//"http://www.baidu.com";// 二维码内容
			String code_path = context_path +"/codepic/"+System.currentTimeMillis()+"_code.png";
            BitMatrix byteMatrix;  
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    		hints.put(EncodeHintType.MARGIN, 1);
            byteMatrix = new MultiFormatWriter().encode(content,  BarcodeFormat.QR_CODE, 260, 260,hints);  
            File file = new File(code_path);  
            MatrixToImageWriter.writeToFile(byteMatrix, "png", file); 
			
            //背景图片
            String bgPicPath = context_path +"/codepic/moban.png";
			BufferedImage big = ImageIO.read(new File(bgPicPath));
			BufferedImage small = ImageIO.read(new File(code_path));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2+171;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			
			ImageIO.write(big, "jpg", new File(path_new));
			file.delete(); //删除二维码图片
			
			FileInputStream hFile = new FileInputStream(path_new); 
			//得到文件大小 
			int i=hFile.available(); 
			byte data[]=new byte[i]; 
			//读数据 
			hFile.read(data); 
			//得到向客户端输出二进制数据的对象
			OutputStream toClient=response.getOutputStream(); 
			//输出数据 
			toClient.write(data); 
			toClient.flush(); 
			toClient.close(); 
			hFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
