# Download library and data for algs assignments
# 2017-12-29
# chaonan99

import urllib.request
import os.path as osp

base_url = "http://coursera.cs.princeton.edu/algs4/testing/"
lib_path = osp.join("lib", "algs4.jar")
# file_list = ["percolation", "queues", "collinear", "8puzzle", "kdtree",
#              "wordnet", "seam", "baseball", "boggle"]
file_list = ["boggle"]

def download_file(url, save_path):
    with urllib.request.urlopen(url) as response, \
    open(save_path, 'wb') as out_file:
        data = response.read()
        out_file.write(data)

def download_data(name):
    file_name = name + "-testing.zip"
    save_path = osp.join("input", file_name)
    if not osp.isfile(save_path):
        download_file(osp.join(base_url, file_name), save_path)

# Download library
if not osp.isfile(lib_path):
    download_file("https://algs4.cs.princeton.edu/code/algs4.jar", lib_path)

# Download data
for f in file_list:
    download_data(f)