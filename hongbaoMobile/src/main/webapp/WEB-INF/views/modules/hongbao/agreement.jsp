<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <link href="${ctxStatic }/modules/hongbao/css/style1.css" rel="stylesheet" type="text/css" />
	<script>
		var staticPath = "${ctxStatic }";
		var path = "${ctx }";
	</script>
	<script src="${ctxStatic }/jquery/jquery-1.8.3.min.js"></script>
	<script src="${ctxStatic }/modules/hongbao/js/fontscroll.js"></script>
	<script src="${ctxStatic }/modules/hongbao/js/module.js"></script>
</head>
<body style="background:none;color:#000;line-height:25px;">
<marquee align="right" style="background-color:rgba(0,0,0,.5);text-align: center;position: relative;" behavior="scroll" scrollamount="1" direction="up" height="30">
	<c:forEach items="${luckyUserList }" var="luckyUser" varStatus="stat">
		恭喜会员【${luckyUser.userNo }】在${luckyUser.amount }元区抽中红包${luckyUser.luckyAmount }元<c:if test="${!stat.last}"><br/><br/><br/></c:if>
	</c:forEach>
</marquee>
<div style="padding:5px;">
您在注册成为拼手气红包用户时，即表示您接受本协议所包含的所有条款和条件的约束。本协议阐述之条款和条件适用于您使用拼手气红包网站所提供的各种服务(下称“服务”)。<br>

1.接受条款。

<br>进入拼手气红包网站即表示您同意自己已经与拼手气红包订立本协议，且您将受本协议的条款和条件(“条款”)约束。拼手气红包网站可随时自行全权决定更改“条款”。如“条款”有任何变更，拼手气红包将在其网站上要求公告，通知予您。如您不同意相关变更，必须停止使用“服务”。经修订的“条款”一经在本网站公布后，立即自动生效。您应在第一次登录后仔细阅读修订后的“条款”，并有权选择停止继续使用“服务”；一旦您继续使用“服务”，则表示您已接受经修订的“条款”，当您与拼手气红包网站发生争议时，应以最新的服务协议为准。除另行明确声明外，任何使“服务”范围扩大或功能增强的新内容均受本协议约束。

<br>2.谁可使用拼手气红包？

<br>“服务”仅供能够根据相关法律规定的有完全民事行为能力的人（自然人或单位和个人）。拼手气红包团队可随时自行全权决定拒绝向任何人士提供“服务”。

<br>3.费用。

<br>拼手气红包保留在根据第1条通知您后，收取“服务”费用的权利。您因进行交易、向拼手气红包获取本平台有偿服务或其他行为而发生的相关税赋和费用均由您自行承担。拼手气红包平台保留在无须发出书面通知，仅在本网站公示的情况下，暂时或永久地更改或停止部分或全部“服务”的权利。

<br>4.1注册义务。

<br>如您在拼手气红包网站注册，您同意：(a)根据拼手气红包所要求的会员资料，提供关于您或贵单位和个人的真实、准确、完整和反映当前情况的资料；(b)维持并及时更新会员资料，使其保持真实、准确、完整和反映当前情况。倘若您提供任何不真实、不准确、不完整或不能反映当前情况的资料，或拼手气红包有合理理由怀疑该等资料不真实、不准确、不完整或不能反映当前情况，拼手气红包有权暂停或终止您的注册身份及资料，并拒绝您在目前或将来对“服务”(或其任何部份)以任何形式使用。如您代表一家单位和个人或其他法律主体在拼手气红包登记，则您声明和保证，您有权使该单位和个人或其他法律主体接受本协议“条款”约束。

<br>4.2会员注册名、密码和保密。

<br>在登记过程中，您将选择会员注册名和密码。您须自行负责对您的会员注册名和密码保密，且须对您在会员注册名和密码下发生的所有活动承担责任。您同意：(a)如发现任何人未经授权使用您的会员注册名或密码，或发生违反保密规定的任何其他情况，您会立即通知拼手气红包；及(b)确保您在每个上网时段结束时，以正确步骤离开网站。拼手气红包不能也不会对因您未能遵守本款规定而发生的任何损失或损毁负责。

<br>4.3用户管理

<br>用户单独承担支付款盈亏的责任。用户对服务的使用是根据所有适用于服务的地方法律、国家法律和国际法律标准的。用户承诺：

<br>（1）在拼手气红包的网页上支付或者利用拼手气红包的服务时必须符合中国有关法规，不得在拼手气红包的网页上或者利用拼手气红包的服务制作、复制、发布、传播以下信息：

<br>(a)反对宪法所确定的基本原则的；

<br>(b)危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；

<br>(c)损害国家荣誉和利益的；

<br>(d)煽动民族仇恨、民族歧视，破坏民族团结的；

<br>(e)破坏国家宗教政策，宣扬邪教和封建迷信的；

<br>(f)散布谣言，扰乱社会秩序，破坏社会稳定的；

<br>(g)散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；

<br>(h)侮辱或者诽谤他人，侵害他人合法权益的；

<br>(i)含有法律、行政法规禁止的其他内容的。

<br>（2）在拼手气红包的网页上使用或者利用拼手气红包的服务时还必须符合其他有关国家和地区的法律规定以及国际法的有关规定。

<br>（3）不利用拼手气红包的服务从事以下活动：

<br>(a)未经允许，进入计算机信息网络或者使用计算机信息网络资源的；

<br>(b)未经允许，对计算机信息网络功能进行删除、修改或者增加的；

<br>(c)未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；

<br>(d)故意制作、传播计算机病毒等破坏性程序的；

<br>(e)其他危害计算机信息网络安全的行为。

<br>（4）不以任何方式干扰拼手气红包的服务。

<br>（5）遵守拼手气红包的所有其他规定和程序。

<br>（6）对于存在恶意刷票行为的问卷，拼手气红包有权在不事先通知用户的情况下直接终止此问卷。

<br>用户须对自己在使用拼手气红包服务过程中的行为承担法律责任。第(1)条所列内容之一，依据中国法律，拼手气红包有权利立即停止向该用户提供服务，删除该用户发布的非法信息，并保存有关记录向国家有关机关报告。

<br>用户使用拼手气红包过程对自己的付款和抢到的金额负责，均为随机金额。并用户在利用本平台推广宣传为自己获得佣金的行为，也须遵守本条的规定以及拼手气红包的服务规则，上段中描述的法律后果和法律责任该部分用户。若用户的行为不符合以上提到的服务条款，拼手气红包将作出独立判断立即取消用户服务帐号。

<br>4.4关于您的资料的规则。

<br>您同意，“您的资料”

<br>a.不会有欺诈成份，与伪造或盗窃无涉；

<br>b.不会侵犯任何第三者享有的物权、版权、专利、商标、商业秘密或其他知识产权，或隐私权、名誉权；

<br>c.不会违反任何法律、法规、条例或规章；

<br>d.不会含有诽谤（包括商业诽谤）、非法恐吓或非法骚扰的内容；

<br>e.不会含有淫秽、或包含任何儿童色情内容；

<br>f.不会含有蓄意毁坏、恶意干扰、秘密地截取或侵占任何系统、数据或个人资料的任何病毒、伪装破坏程序、电脑蠕虫、定时程序炸弹或其他电脑程序；

<br>g.不会直接或间接与下述各项服务连接，或包含对下述各项服务的描述：(i)本协议项下禁止的服务；或(ii)您无权连接或包含或服务。此外，您同意不会：(h)在与任何连锁信件、大量胡乱邮寄的电子邮件、滥发电子邮件或任何复制或多余的信息有关的方面使用“服务”；(i)未经其他人士同意，利用“服务”收集其他人士的电子邮件地址及其他资料；或(j)利用“服务”制作虚假的电子邮件地址，或以其他形式试图在发送人的身份或信息的来源方面误导其他人士。

<br>5.隐私。

<br>拼手气红包将仅根据拼手气红包的隐私声明使用“您的资料”。拼手气红包隐私声明的全部条款属于本协议的一部份，因此，您必须仔细阅读。微信红包承诺绝对不会在未经您同意的情况下有意或无意向任何第三方泄漏您的隐私。

<br>7.终止或访问限制。

<br>您同意，根据本协议的任何规定终止您使用“服务”之措施可在不发出事先通知的情况下实施，并承认和同意，拼手气红包可立即使您的帐户无效，或撤销您的帐户以及在您的帐户内的所有相关资料和档案，和/或禁止您进一步接入该等档案或“服务”。帐号终止后，拼手气红包没有义务为您保留原帐号中或与之相关的任何信息，或转发任何未曾阅读或发送的信息给您或第三方。此外，您同意，拼手气红包不会就终止您接入“服务”而对您或任何第三者承担任何责任。第9、10、11和19各条应在本协议终止后继续有效。

<br>8.违反规则会有什么后果？

<br>在不限制其他补救措施的前提下，发生下述任一情况，拼手气红包可立即发出警告，暂时中止、永久中止或终止您的会员资格，删除您的任何现有信息，以及您在网站上展示的任何其他资料：(i)您违反本协议；(ii)拼手气红包无法核实或鉴定您向拼手气红包提供的任何行为；或(iii)拼手气红包相信您的行为可能会使您、拼手气红包用户或通过拼手气红包提供服务的第三者服务供应商发生任何法律责任。在不限制任何其他补救措施的前提下，倘若发现您从事涉及利用拼手气红包平台进行诈骗、洗钱、赌博等活动，拼手气红包可暂停或终止您的帐户。

<br>9.服务“按现状”提供。

<br>拼手气红包会尽一切努力使您在使用拼手气红包网站的过程中得到乐趣。遗憾的是，拼手气红包不能随时预见到任何随机或技术上的问题或其他困难。该等困难可能会导致数据损失或其他服务中断。为此，您明确理解和同意，您使用“服务”的风险由您自行承担。“服务”以“按现状”和“按可得到”的基础提供。拼手气红包明确声明不作出任何种类的所有明示或暗示的保证，包括但不限于关于适销性、适用于某一特定用途和无侵权行为等方面的保证。拼手气红包对下述内容不作保证：(i)“服务”会符合您的要求；(ii)“服务”不会中断，且适时、安全和不带任何错误；(iii)通过使用“服务”而可能获取的结果将是准确或可信赖的；及(iv)您通过“服务”而购买或获取的任何产品、服务、资料或其他材料的质量将符合您的预期。通过使用“服务”产生的资金损失是由您自行全权决定进行的，且与此有关的风险由您自行承担，对于因您使用平台过程中而发生的您的电脑系统的任何损毁或任何数据损失，您将自行承担责任。您从拼手气红包或通过或从“服务”获取的任何口头或书面意见或资料，均不产生未在本协议内明确载明的任何保证。

<br>10.责任范围。

<br>您明确理解和同意，拼暑期红包不对因下述任一情况而发生的任何损害赔偿承担责任，包括但不限于利润、商誉、使用、数据等方面的损失或其他无形损失的损害赔偿(无论拼手气红包是否已被告知该等损害赔偿的可能性)：(i)使用或未能使用“服务”；(ii)因通过或从“服务”使用各种服务，或通过或从“服务”产生的费用；(iii)未经批准接入或更改您的传输资料或数据；(iv)任何第三者对“服务”的声明或关于“服务”的行为；或(v)因任何原因而引起的与“服务”有关的任何其他事宜，包括疏忽。

<br>11.赔偿。

<br>您同意，因您违反本协议或经在此提及而纳入本协议的其他文件，或因您违反了法律或侵害了第三方的权利，而使第三方对拼手气红包、董事、职员、代理人提出索赔要求（包括司法费用和其他专业人士的费用），您必须赔偿给拼手气红包、董事、职员、代理人，使其等免遭损失。

<br>12.遵守法律。

<br>您应遵守与您使用“服务”有关的所有相关的法律、法规、条例和规章。

<br>13.无代理关系。

<br>您与拼手气红包仅为独立订约人关系。本协议无意结成或创设任何代理、合伙、合营、雇用与被雇用或特许权授予与被授予关系。

<br>14.链接。

<br>“服务”或第三者均可提供与其他网站或资源的链接。由于拼手气红包并不控制该等网站和资源，您承认并同意，拼手气红包并不对该等外在网站或资源的可用性负责，且不认可该等网站或资源上或可从该等网站或资源获取的任何内容、宣传、产品、服务或其他材料，也不对其等负责或承担任何责任。您进一步承认和同意，对于任何因使用或信赖从此类网站或资源上获取的此类内容、宣传、产品、服务或其他材料而造成（或声称造成）的任何直接或间接损失，拼手气红包均不承担责任。
<br>16.不可抗力。

<br>对于因拼手气红包合理控制范围以外的原因，包括但不限于自然灾害、罢工或骚乱、物质短缺或定量配给、暴动、战争行为、政府行为、通讯或其他设施故障或严重伤亡事故等，致使拼手气红包延迟或未能履约的，拼手气红包不对您承担任何责任。

<br>18.转让。

<br>拼手气红包转让本协议无需经您同意。

<br>19.其他规定。

<br>本协议构成您和拼手气红包之间的全部协议，规定了您对“服务”的使用，并取代您和拼手气红包先前订立的任何书面或口头协议。本协议各方面应受中华人民共和国大陆地区法律的管辖。倘若本协议任何规定被裁定为无效或不可强制执行，该项规定应被撤销，而其余规定应予执行。条款标题仅为方便参阅而设，并不以任何方式界定、限制、解释或描述该条款的范围或限度。拼手气红包未就您或其他人士的某项违约行为采取行动，并不表明拼手气红包撤回就任何继后或类似的违约事件采取行动的权利。

<br>20.诉讼。

<br>因本协议或拼手气红包服务所引起或与其有关的任何争议应向人民法院提起诉讼，并以中华人民共和国法律为管辖法律。
<br>
<br>
<br>
</div>
<footer></footer>
</body>
</html>