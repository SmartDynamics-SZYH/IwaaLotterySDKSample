# 神州云海IwaaLotterySDK集成说明文档V1.0.0 #
## 概述 ##
本文档是在Android 5.1系统上，Android程序集成彩票机器人相关功能SDK的说明文档。    
IwaaLotterySDK 提供主控板通信，六麦，分票器和扫描头相关功能；  

## 运行基础 ###
1. 机器人必须先安装LotterySdk-release-1.0.2.apk，提供底层服务能力   


## 集成步骤说明 ###
1. 把aar文件 拷贝到 自己Android工程的app/libs目录下；  

2. 在app/build.gradle中添加如下	 

```
repositories {
	flatDir {
		dirs 'libs'
	}
}
dependencies {
    ...
    implementation(name: 'LotteryRosSDK-release-1.0', ext: 'aar')
    ...
}
```  
或者简洁版

```
dependencies {
	implementation fileTree(dir: 'libs', include: ['*.jar', '*.aar'])
	implementation 'com.alibaba:fastjson:1.1.68.android'
}
```  
3. 在AndroidManifest.xml中添加权限，如有则忽略 

```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```  
# 功能说明 #
## 功能模块 ##
### 主控板通信模块 ###
相关API全部封装在RobotRosApi类，使用的时候可以通过RobotRosApi.get()获取对象，再调用相应的方法。	
查询主控版本信息  
DeviceVersion queryMainBroadVersion(boolean isReQuery)

	DeviceVersion 属性说明
	type:版本号类型
	version:版本号,主版本.次版本.修订版本号
	revisionDate:修订日期年月日（2018-9-1）	

添加电池信息监听器  
	addBatteryStatusListener(BatteryInfoListener listener) 
删除电池信息监听器   
	removeBatteryStatusListener(BatteryInfoListener listener)
	
	BatteryInfo 属性说明
	mChargeStatus: 充电状态，1表示充电中，0表示未充电
	mBatteryCurrent:电池电流mA
	mBatteryVoltage:电池电压mV		
	mBatteryGasGauge:电池电量mAH
	mBatteryTemperature:电池温度 ℃
	mBatteryGasGaugePercent:电池电量百分比%
	
添加呼吸灯信息监听器  
	addBreathLightInfoListener(BreathLightInfoListener listener) 

删除呼吸灯信息监听器  
	removeBreathLightInfoListener(BreathLightInfoListener listener) 

	BreathLightInfo 属性说明
	mIsOpen: 呼吸灯开关状态，true表示打开，false表示关闭
	mRedValue: 呼吸灯RGB红色值
	mGreenValue: 呼吸灯RGB绿色值
	mBlueValue: 呼吸灯RGB蓝色值

添加头部运动信息监听器  
	addHeadInfoListener(HeadInfoListener listener) 

删除头部运动信息监听器  
	removeHeadInfoListener(HeadInfoListener listener) 

	Head 属性说明
	angle: 头部转向角度
	speed: 头部运动速度


添加传感器信息监听器  
	addSensorStatusListener(SensorStatusListener listener)

删除传感器信息监听器  
	removeSensorStatusListener(SensorStatusListener listener) 

	SensorInfo 属性说明
	redStatus: 红外感应人体状态， 1表示感应到人体，0表示未感应到人体
	sharkStatus: 机器人震动状态， 1表示感应到机器在震动，0表示未感应到机器在震动
	doorStatus: 票仓门状态， 1表示票仓门关闭，0表示票仓门打开（特别该项注意1，0值的含义）

定时开关机模式下，满足条件下，延时关机的时间设置（到达定时时间，主控板根据该时间设置，提前通知应用层）  
setSleepDelayTime(int min)

执行休眠操作开关，设置true后，即使满足休眠条件，也不执行休眠动作 表示休眠模式阻止，false 表示休眠模式开始  
	exeSleepSwitch(boolean isOpen)

向主控板发送二维码扫描成功标志  
	sendTickEncodeReadFlag() 

设置耳朵呼吸灯颜色  
	setBreathingLightRGB(int red, int green, int blue)

切换表情屏的表情  
	showExpressionById(int id)

设置关机模式 mode： 0 正常关机模式 1 断电关机模式 2 定时关机模式  
	sleepModeChange(int mode)

二维码触发器控制  
	switchBCTrigCtrl(boolean isOpen)

呼吸灯开关  
	switchBreathingLightCtrl(boolean isOpen)

电磁锁开关  
	switchElectLockCtrl(boolean isOpen)

校验MCU时间,把当前安卓板时间同步给MCU  
	syncMcuTime()

头部运动到某个角度  
	turnHeadTo(int angle)

更新MCU进入休眠的时间  
	updateMcuSleepTime(int hour, int minute, int second)

开始头部运行  
	startHeadLoop()

停止头部运行  
	stopHeadLoop()

关机  
	switchPowerRelayCtrl()

升级mcu 主控板程序  
	mcuUpdate(String path, McuUpdateListener listener)

### 分票器模块 ###
相关API全部封装在RobotTicketApi类，使用的时候可以通过RobotTicketApi.get()获取对象，再调用相应的方法。
	 
（1）分票器设备是否被打开, 标识跟分票器设备是否建立正常通信  
	boolean isOpenDev()  

（2）修复出票机（解决出票机在某些情况下的出票失败， 如卡纸）  
	 repairTD()   

（3）查询出票机版本号  
	 queryVersion()  
	 
（4）分票器业务状态, true 标识出票机正在进行某次出票业务，需要等待TicketOutResult中的回调方法，才能再次调用出票方法  
	boolean isTicketTriggering()

（5）初始化出票机版本出票状态, 开机时候和明确知道出票机没有正在出票时候可以调用  
	resetTicketTriggering() 

（6）出票 count: 出票数量，TicketOutResult 出票结果回调  
	 triggerTicketOut(int count, TicketOutResult result) 

（7）删除出票结果监听，防止内存泄漏  
	 unRegisterTicketOutCallback() 

（8）停止出票  
	 stopTriggerOut()

（9）获取分票器接触头纸张状态  
     getTicketStatus();

	DispenserStatus 属性说明
	mLackStatus: 通道状态: 0x00有纸，0x01缺纸

（10）设置票种  
     setTicketType(byte type)

	type 说明
	0x01: 2元票 51mm
	0x02: 3元票 64mm
	0x03: 10元票 152mm
	0x04: 20元票 204mm
	0x05: 30元票 254mm

（11）设置票长 设置票长当前支持 0x03(10元票), 单位毫米  
     setTicketLength(int mm)

（12）升级分票器固件  
     updateFirmware(String type, String path, TDUpdateListener listener)

### 扫描头模块 ###
相关API全部封装在RobotScanCodeApi类，使用的时候可以通过RobotScanCodeApi.get()获取对象，再调用相应的方法。
	 
（1）获取扫描头固件版本号  
	queryVersion() 

（2）添加扫描结果监听器  
	 addQRCoderListener(@NonNull QRCoderListener listener) 

（3）删除扫描结果监听器  
	 removeQRCoderListener(@NonNull QRCoderListener listener)  

### 六麦模块 ###
相关API全部封装在RobotMicApi类，使用的时候可以通过RobotMicApi.get()获取对象，再调用相应的方法。
	 
（1）获取扫描头固件版本号  
	queryMicCoreBroadVersion(boolean isReQuery) 

（2）查询唤醒角度  
	 queryAwakenAngle() 

（3）增强某一序号麦的拾音效果  
	 enhanceBeam(int micIndex) 

（4）重置  
	 reset() 

（5）设置唤醒 false 表示关闭唤醒，true表示开启唤醒  
	 enableAwaken(boolean enable) 

（6）注册六麦唤醒监听器  
	 registerMicAwakenListener(@NonNull MicAwakenListener micAwakenListener)

（7）反注册六麦唤醒监听器  
	 unregisterMicAwakenListener(@NonNull MicAwakenListener micAwakenListener)





