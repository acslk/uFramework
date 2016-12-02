import http.client

conn = http.client.HTTPConnection("localhost:9000")

for i in range(1000):
	conn.request("GET", "/")
	resp = conn.getresponse()
	data = resp.read()
	if data != b'user input':
		print("error")