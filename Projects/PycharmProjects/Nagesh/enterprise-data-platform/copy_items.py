import os
from shutil import copy

base_source_path = "/home/c14795/My Wedding Pics/WEDDING PHOTOS"
base_target_path = "/home/c14795/My Wedding Pics"
file_ext = ".JPG"
with open(
    '/home/c14795/PycharmProjects/Nagesh/enterprise-data-platform/file_names') as f: file_names = f.read().split(
    ",")


source_folder = "1"
file_suffix = "ROSE"
target_folder = "bujji vadina"


if not os.path.exists(os.path.join(base_target_path, target_folder)):
    os.mkdir(os.path.join(base_target_path, target_folder))
for file_name in file_names:
    copy(os.path.join(base_source_path, source_folder, file_suffix + file_name + file_ext),
         os.path.join(base_target_path, target_folder))
