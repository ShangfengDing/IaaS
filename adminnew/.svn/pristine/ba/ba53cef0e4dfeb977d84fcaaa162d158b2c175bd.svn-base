#!/bin/bash
# 简单粗暴适合 乐视的json，不是标准json，字段不加“”，参数全是字符串
#./csvtojson.sh input.csv > output.json 
 
input=$1

[ -z $1 ] && echo "GAUSS@ Error: CSV file not found" && exit 1



[ ! -e $input ] && echo "GAUSS@ Error: couldn't find $1" && exit 1


read first_line < $input     
index=0                 
attributes=`echo $first_line | awk -F, {'print NF'}`
lines=`cat $input | wc -l`    

while [ $index -lt $attributes ]
do
        head_array[$index]=$(echo $first_line | awk -v x=$(($index + 1)) -F"," '{print $x}')
        index=$(($index+1))
done




ix=0
echo -n "["
while [ $ix -lt $lines ]
do
        read each_line
        if [ $ix -ne 0 ]; then
                d=0
                echo -n "{"
                while [ $d -lt $attributes ]
                do
                        each_element=$(echo $each_line | awk -v y=$(($d + 1)) -F"," '{print $y}')
                        if [ $d -ne $(($attributes-1)) ]; then
                                echo -n ${head_array[$d]}":"'"'$each_element'"'","
                        else
                                echo -n ${head_array[$d]}":"'"'$each_element'"'
                        fi
                        d=$(($d+1))
                done
                if [ $ix -eq $(($lines-1)) ]; then
                        echo -n "}"
                else
                        echo -n "},"
                fi
        fi
        ix=$(($ix+1))
done < $input
echo -n "]"
