setlocal
cd /D %0\..
call mvn compile  
call mvn -Dmaven.test.skip=true package  
echo ============================================================================  
echo =========================== package is complete=============================  
pause