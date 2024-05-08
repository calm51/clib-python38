#!/usr/bin/python3
import os, sys, shutil, subprocess, time, json

cwd = os.path.dirname(os.path.abspath(__file__))

"""
python3
%{sourceDir}/data/src/change_android_settings.py
%{sourceDir}

ADD_CUSTOM_COMMAND(TARGET ${PROJECT_NAME} POST_BUILD COMMAND python3 "${CMAKE_SOURCE_DIR}/script/change_android_settings.py") # PRE_BUILD | PRE_LINK| POST_BUILD

"""

_name = "openssldemo"
if __name__ == "__main__":
    source_dir = os.path.split(cwd)[0]
    script = os.path.join(source_dir, "script", "change_android_settings.py")
    if not os.path.isfile(script):
        raise ValueError(script)
    print(os.name)
    if "debug" in sys.argv:
        build_dirname = f"build-{_name}-Qt_5_15_2_Clang_Multi_Abi-Debug"
    else:
        build_dirname = f"build-{_name}-Qt_5_15_2_Clang_Multi_Abi-Release"
    root_dirpath = os.path.split(source_dir)[0]
    build_dirpath = os.path.join(root_dirpath, build_dirname)
    build_settings = os.path.join(build_dirpath, "android_deployment_settings.json")
    if not os.path.isfile(build_settings) or not os.path.exists(build_settings):
        raise ValueError(build_settings)
    #    shutil.copytree(os.path.join(build_dirpath,"translations"),os.path.join(build_dirpath,"android-build","translations"))
    _lib_path = os.path.join(root_dirpath, "..", "output", "lib", )
    if not os.path.isdir(_lib_path): _lib_path = os.path.join(source_dir, "lib", )
    if not os.path.exists(os.path.join(build_dirpath, "libcrypto.so")):
        shutil.copy(os.path.join(_lib_path, "android", "openssl", "libcrypto.so.1.1"),
                    os.path.join(build_dirpath, "libcrypto.so"))
        shutil.copy(os.path.join(_lib_path, "android", "openssl", "libssl.so.1.1"),
                    os.path.join(build_dirpath, "libssl.so"))
    with open(build_settings, "r", encoding="utf8") as f:
        j: dict = json.loads(f.read())
        j["android-package-source-directory"] = os.path.join(source_dir, "android")
        # j["android-min-sdk-version"] = "21"
        j["android-extra-libs"] = ",".join([
            os.path.join(build_dirpath, "libcrypto.so"),
            os.path.join(build_dirpath, "libssl.so"),
            # s.path.join(source_dir,"lib","android","python3.8","libpython3.8.so"),
            # os.path.join(source_dir,"lib","android","python3.8","libpython3.so"),
        ])
    with open(build_settings, "w", encoding="utf8") as f:
        f.write(json.dumps(j, indent=4))
    print("ok.")
