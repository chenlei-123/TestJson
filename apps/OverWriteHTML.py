# -*- coding: utf-8 -*-
import sys,os

import re

def searchString(text, pattern):
    """ test: 被搜索的源字符串
        pattern: 正则表达式语法
        index: 要获取的分组结果索引
    """
    m = re.search(pattern, text)
    if m:
        return m.group(0)


def ReplaceFileContentDebugTool(filename, srcpattern, dsttext):
    '''替换文件中的内容,支持正则表达式语法'''
    file = open(filename, "r")
    lines = file.readlines()
    bFound = False
    output = ""
    for line in lines:
        findstr = searchString(line, srcpattern)
        if findstr:
            print "Find Text:" + findstr
            line = line.replace(line, dsttext)
            bFound = True
        output = output + line
    file.close()

    new_file = open(filename, "w")
    new_file.writelines(output)
    new_file.close()
    return bFound



def readhtmlFile(path):
    html_file = open(path+'/target/surefire-reports/html/overview.html',"r")
    try:
        html_content = html_file.read()
    finally:
        html_file.close()
    return html_content


def readCssFile(path):
    file_object = open(path+'/target/surefire-reports/html/reportng.css', "r")
    try:
        css_text = file_object.read()
    finally:
        file_object.close()
    css_text = '<style>'+css_text+'</style>'
    return css_text

def readjsFile(path):

    js_file = open(path+'/target/surefire-reports/html/reportng.js',"r")
    try:
        file_content = js_file.read()
    finally:
        js_file.close()
    file_content = '<script>'+file_content+'</script>'
    return  file_content
if __name__ == '__main__':
    path = os.getcwd()

    new_content = readCssFile(path)+readjsFile(path)
    ReplaceFileContentDebugTool(path+'/target/surefire-reports/html/overview.html',"<link href=\"reportng.css\" rel=\"stylesheet\" type=\"text/css\" />",new_content)
    ReplaceFileContentDebugTool(path+'/target/surefire-reports/html/overview.html','<body>',"<body style=\"padding:50px\">")



