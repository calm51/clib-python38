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

#include <QNetworkAccessManager>
#include <qmessagebox.h>

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

    QNetworkAccessManager networkManager;
    //    if(!QSslSocket::supportsSsl()){
    QStringList sl;
    sl << "是否支持SSL:  " << (QSslSocket::supportsSsl()?"支持":"不支持")
       << "\nLib Version Number: " << QString::number(QSslSocket::sslLibraryVersionNumber())
       << "\nLib Version String: " << QSslSocket::sslLibraryVersionString()
       << "\nLib Build Version Number: " << QString::number(QSslSocket::sslLibraryBuildVersionNumber())
       << "\nLib Build Version String: " << QSslSocket::sslLibraryBuildVersionString()
       <<"\n"<<networkManager.supportedSchemes().join(", ");
    QMessageBox::information(&w, " ",sl.join(""));
    //    }

    return a.exec();

}

