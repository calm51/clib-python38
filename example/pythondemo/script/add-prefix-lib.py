import os
cwd = os.path.dirname(os.path.abspath(__file__))
if __name__ == "__main__":
    source_dir = os.path.split(cwd)[0]
    so_dir = os.path.join(source_dir,"resource","python3.8-so")
    os.chdir(so_dir)
    for i in os.listdir(so_dir):
        if not i.startswith("lib"):
            os.rename(i,"lib"+i)
