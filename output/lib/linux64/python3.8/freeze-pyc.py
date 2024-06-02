import os
import shutil
import sys

if len(sys.argv) == 2:
    print(sys.argv[1], sys.argv[1] + "o", os.path.split(sys.argv[1])[1])
    # freeze_as_data(sys.argv[1], sys.argv[1] + "o", os.path.split(sys.argv[1])[1])
    exit(0)

source_folder = "./python3.8-py"
destination_folder = "./python3.8-pyc"
shutil.copytree(source_folder, destination_folder, dirs_exist_ok=True)
os.system(f"python3 -OO -m compileall -b -f {destination_folder}")
os.system(f"""find {destination_folder} -type f -name "*.py" -delete""")
os.system(f"""find {destination_folder} """ + """-type d -name "__pycache__" -exec rm -r {} +""")
