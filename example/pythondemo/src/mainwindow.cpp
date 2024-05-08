#include "mainwindow.h"
#include "./ui_mainwindow.h"


#if defined Q_OS_ANDROID
#include "QtAndroid"
#include <QtAndroidExtras>
#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>

#include <qtandroidcls/qtandroidcls.h>
#endif

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);


}

MainWindow::~MainWindow(){
    delete ui;
}

void MainWindow::closeEvent(QCloseEvent *event){
    QMainWindow::closeEvent(event);
    qApp->exit(0);
}
