#!/usr/bin/python
# Copyright (c) 2014 GRAU DATA AG
#
# This file is part of the OpenDataSpace CMIS Benchmark.
#
# The OpenDataSpace CMIS Benchmark is free software: you can redistribute
# it and/or modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation, version 3 of the License
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

"""replace common arguments in jmx files"""
from optparse import OptionParser
from xml.etree import ElementTree as et


def parse_arguments():
    """parse command line arguments"""
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
        parser.error("--clean can not be combined with any other parameter")
    if len(args) == 0:
        parser.error("Please supply filename(s)")

    return (options, args)


def replace_argument(tree, name, value):
    """replace java runner argument in xml tree"""
    for element in tree.findall('.//elementProp'):
        if(element.attrib.get('name') == name):
            for sub in element.findall('stringProp'):
                if(sub.attrib.get('name') == 'Argument.value'):
                    sub.text = value


def handle_file(filename, pairs):
    """parses xml file and writes changes back"""
    tree = et.parse(filename)
    for key, value in pairs.items():
        replace_argument(tree, key, value)
    tree.write(filename)


def main():
    """main function"""
    (options, args) = parse_arguments()
    pairs = {}
    if options.clean:
        for arg in ['user', 'pw', 'host']:
            pairs[arg] = 'CHANGEME'
    else:
        if options.user:
            pairs['user'] = options.user
        if options.pw:
            pairs['pw'] = options.pw
        if options.host:
            pairs['host'] = options.host

    print pairs
    for filename in args:
        handle_file(filename, pairs)


if __name__ == "__main__":
    main()
