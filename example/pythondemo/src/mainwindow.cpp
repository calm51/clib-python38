#include "mainwindow.h"
#include "./ui_mainwindow.h"


#if defined Q_OS_ANDROID
#include "QtAndroid"
#include <QtAndroidExtras>
#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>

#include <qtandroidcls/qtandroidcls.h>
#endif


#pragma push_macro("slots")
#undef slots
#include "Python.h"
#pragma pop_macro("slots")

//#include "qtpyandassets/pyqtdeploy_start.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    connect(ui->pushButton,&QPushButton::clicked,this,[=](){


    });

}

MainWindow::~MainWindow(){
    delete ui;
}

void MainWindow::closeEvent(QCloseEvent *event){
    QMainWindow::closeEvent(event);
    qApp->exit(0);
}
