# -*- coding: utf-8 -*-
import sys,os
import shutil


if __name__ == '__main__':
    arg = sys.argv[1]
    rootPath = os.getcwd()
    print rootPath
    if(os.path.exists(rootPath+"/artifact/"+arg+"/")):
        print ""
    else:
        os.makedirs(rootPath+"/artifact/"+arg+"/")
    names = os.listdir(rootPath+"/target/surefire-reports/html")
    for name in names:
        print name
        shutil.copyfile(rootPath+"/target/surefire-reports/html/"+name,rootPath+"/artifact/"+arg+"/"+name)

