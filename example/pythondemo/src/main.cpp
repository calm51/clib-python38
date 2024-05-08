#pragma once
#pragma execution_character_set("utf-8")

#include "mainwindow.h"
#include <QApplication>
#include <QDebug>
#include <QPair>

#if defined Q_OS_ANDROID
#include "QtAndroid"
#include <QtAndroidExtras>
#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>

#include <qtandroidcls/qtandroidcls.h>
#endif

#include <qmessagebox.h>

#pragma push_macro("slots")
#undef slots
#include "Python.h"
#pragma pop_macro("slots")

int main(int argc, char *argv[]){
    QApplication::setHighDpiScaleFactorRoundingPolicy(Qt::HighDpiScaleFactorRoundingPolicy::PassThrough);
    QApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
    QApplication::setAttribute(Qt::AA_UseHighDpiPixmaps);
    QApplication a(argc, argv);
    a.setAttribute(Qt::ApplicationAttribute::AA_UseHighDpiPixmaps);
    a.setQuitOnLastWindowClosed(false);
    a.setStyle("Fusion");
#if defined Q_OS_ANDROID
    QtAndroidCls *qac = QtAndroidCls::instance("ca/calm/openssldemo/openssldemo");
#endif
    a.setWindowIcon(QIcon(":/resource/icon/main.ico"));

    MainWindow w;
    w.setWindowTitle("openssldemo");
#ifdef Q_OS_ANDROID
    w.show();
    QTimer::singleShot(50,qac,[&](){
        qac->to_statusbar_text_white();
        QtAndroid::hideSplashScreen(100);
        qac->to_statusbar_text_black();
    });
    qac->up_statusbar_height();
#endif
    w.show();

    if (QString::fromWCharArray(Py_GetProgramName()).count()==0){ Py_SetProgramName(QString("python3").toStdWString().c_str()); }

    Py_Initialize();

    const char* code = R"(
print("Hi, I am python3.8.")
  )";
    PyRun_SimpleString(code);

    Py_Finalize();

    return a.exec();

}

