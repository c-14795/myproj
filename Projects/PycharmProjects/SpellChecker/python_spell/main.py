sample = {'1':22,'2':34,'4':55}

for i in sorted(sample,key=sample.get,reverse=True):
    print i

a= ''