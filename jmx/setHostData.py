#!/usr/bin/python
"""replace common arguments in jmx files"""
from optparse import OptionParser
from xml.etree import ElementTree as et


def parse_arguments():
    usage = "usage: %prog [options] file1 file2"
    parser = OptionParser(usage, version="%prog 1.0")

    parser.add_option(
        "-c", "--clean", action="store_true",
        dest="clean", default=False,
        help="clean up xml to check into github"
        )
    parser.add_option("-u", "--user", dest="user", help="Username")
    parser.add_option("-p", "--password", dest="pw", help="Password")
    parser.add_option("-a", "--address", dest="host", help="host-address")

    (options, args) = parser.parse_args()

    if options.clean and (options.user or options.pw or options.host):
        parser.error("--clean can not be combined with anything but --file")
    if len(args) == 0:
        parser.error("Please supply filename(s)")

    return (options, args)


def replaceArgument(tree, name, value):
    """replace java runner argument in xml tree"""
    xpath = ".//elementProp[@name='{}']/stringProp[@name='Argument.value']"
    for host in tree.findall(xpath.format(name)):
        host.text = value


def handleFile(file, pairs):
    tree = et.parse(file)
    for key, value in pairs.items():
        replaceArgument(tree, key, value)
    tree.write(file)


(options, args) = parse_arguments()
pairs = {}
if options.clean:
    for a in ['user', 'pw', 'host']:
        pairs[a] = 'CHANGEME'
else:
    if options.user:
        pairs['user'] = options.user
    if options.pw:
        pairs['pw'] = options.pw
    if options.host:
        pairs['host'] = options.host

print pairs
for file in args:
    handleFile(file, pairs)
