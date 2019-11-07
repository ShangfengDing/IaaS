lol agent
sleep 5
for file in `ls logs/ | awk -F. '{print $1}' | uniq `; do lol addtailer logs/$file.log yh35.$file; done;
