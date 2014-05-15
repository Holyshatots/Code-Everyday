#!/bin/sh
airmon-ng start wlan1
echo 'Started wlan1'
airbase-ng -e RainbowDash -q mon0 &
echo 'Started access point'
sleep 3
ifconfig at0 up
echo 'Brought at0 up'
ifconfig at0 192.168.1.1 netmask 255.255.255.0
echo 'Assigned at0 ip 192.168.1.1'
#Kill off dnsmasq
killall dnsmasq
service dnsmasq start
