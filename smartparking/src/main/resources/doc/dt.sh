#/bin/sh
BASE_PATH=$(cd `dirname $0`; pwd)
JAR_NAME=`find $BASE_PATH -maxdepth 1 -name *.jar`
API_NAME=${JAR_NAME%.*}
JAR_NAME=${JAR_NAME##*/}
xJarName=${JAR_NAME%-*}
# PID=$API_NAME\.pid
echo '名称===>'$JAR_NAME
#使用说明，用来提示输入参数
usage() {
    echo "Usage: dt.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep ${xJarName%-*}|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then 
    echo ">>> $JAR_NAME is already running PID=${pid} <<<" 
  else 
	  nohup java -jar $JAR_NAME > nohup.out 2>&1 &
    # echo $! > $PID
    pid=`ps -ef|grep ${JAR_NAME}|grep -v grep|awk '{print $2}' `
    echo ">>> start $JAR_NAME successed PID=${pid} <<<" 
   fi
  }
 
#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then 
    echo ">>> ${API_NAME##*/} 2 PID = $pid begin kill -9 $pid  <<<"
    kill -9  $pid
    sleep 2
    echo ">>> $JAR_NAME process stopped <<<"  
  else
    echo ">>> $JAR_NAME is not running <<<"
  fi  
}
 
#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> $JAR_NAME is running PID is ${pid} <<<"
  else
    echo ">>> $JAR_NAME is not running <<<"
  fi
}
 
#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
exit 0
