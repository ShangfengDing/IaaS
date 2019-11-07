appKey="606ac2f86cf5470c87c187b954ed6b01"
secretKey="w3qqqFYfzK1hCeB9B5j6vk0OshSyGelY"

time=$(date +%s) 

dstat --float -T -cdgnm --disk-util --output /root/dstat/$time.csv 10 5

sed -i '1,7d' /root/dstat/$time.csv 

arg=`/sbin/ifconfig br0 |grep "inet addr"| cut -f 2 -d ":"|cut -f 1 -d " "`
 
ipaddr=$arg

sed -i 's/.*/'${ipaddr}',&/' /root/dstat/$time.csv

sed -i '1i ipaddress,epoch,usr,sys,idl,wai,hiq,siq,read,writ,in,out,recv,send,used,buff,cach,free,sda,sdb' /root/dstat/$time.csv

json="$(/root/monitorshell/./csvtojsonlog.sh /root/dstat/$time.csv)"
#jsonurl=$(/root/monitorshell/./urlencode.sh /root/dstat/$time.csv)
message="appName=cloudmonitortestc=${json}"

jsonur=${json//\"/%22}
jsonurl=${jsonur//\,/%2c}

hashmessage="$(echo -n ${message}|md5sum|awk '{print $1}')"

tc="$(date +%s)000"
nsign="${hashmessage}${appKey}${secretKey}${tc}"
sign="$(echo -n ${nsign} | md5sum | awk '{print $1}')"

#echo "jsonurl"
#echo $jsonurl
#echo "-------"
#echo "第一次未hash"
#echo $message
#echo "------"
#echo "第一次hash"
#echo $hashmessage
#echo "-----"
#echo "nosign"
#echo $nsign
#echo "------"
#echo "sign"
#echo $sign
#echo $time
#echo $tc

curl http://dc.free4inno.com/rest/logs -X POST -d appName=cloudmonitortest -d c=${jsonurl} -H "appName:cloudmonitortest" -H "tc:${tc}" -H "appKey:${appKey}" -H "sign:${sign}"
