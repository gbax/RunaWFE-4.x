@echo off
REM JBoss, the OpenSource webOS
REM
REM Distributable under LGPL license.
REM See terms of license at gnu.org.
REM
REM -------------------------------------------------------------------------
REM JBoss Service Script for Windows
REM -------------------------------------------------------------------------

cd /D  "%0\.."
@if not "%ECHO%" == "" echo %ECHO%
@if "%OS%" == "Windows_NT" setlocal
set DIRNAME=%CD%

REM
REM VERSION, VERSION_MAJOR and VERSION_MINOR are populated
REM during the build with ant filter.
REM
set SVCNAME=JBAS42SVC
set SVCDISP=RunaWFE Server %VERSION%
set SVCDESC=RunaWFE Server %VERSION%
set NOPAUSE=Y

REM Figure out the running mode

@if "%1" == "install"   goto cmdInstall
@if "%1" == "uninstall" goto cmdUninstall
@if "%1" == "start"     goto cmdStart
@if "%1" == "stop"      goto cmdStop
@if "%1" == "restart"   goto cmdRestart
@if "%1" == "signal"    goto cmdSignal
echo Usage: service install^|uninstall^|start^|stop^|restart^|signal
goto cmdEnd

REM jbosssvc retun values
REM ERR_RET_USAGE           1
REM ERR_RET_VERSION         2
REM ERR_RET_INSTALL         3
REM ERR_RET_REMOVE          4
REM ERR_RET_PARAMS          5
REM ERR_RET_MODE            6

:errExplain
@if errorlevel 1 echo Invalid command line parameters
@if errorlevel 2 echo Failed installing %SVCDISP%
@if errorlevel 4 echo Failed removing %SVCDISP%
@if errorlevel 6 echo Unknown service mode for %SVCDISP%
goto cmdEnd

:cmdInstall
jbosssvc.exe -iwdc %SVCNAME% "%DIRNAME%" "%SVCDISP%" "%SVCDESC%" service.bat
@if not errorlevel 0 goto errExplain
echo Service %SVCDISP% installed
goto cmdEnd

:cmdUninstall
call shutdown -S -s jnp://localhost:10099 >shutdown.log  2>nul
@if not errorlevel 0 goto skipWaitStop
ping -n 20 -w 1000 127.0.0.1 > nul
:skipWaitStop
jbosssvc.exe -u %SVCNAME%
@if not errorlevel 0 goto errExplain
echo Service %SVCDISP% removed
goto cmdEnd

:cmdStart
REM Executed on service start
call standalone.bat >run.log
goto cmdEnd

:cmdStop
REM Executed on service stop
call shutdown -S -s jnp://localhost:10099 >shutdown.log
goto cmdEnd

:cmdRestart
REM Executed on service restart
REM Note. We can only stop and start
call shutdown -S -s jnp://localhost:10099 >>shutdown.log
call standalone.bat >>run.log
goto cmdEnd

:cmdSignal
REM Send signal to the service.
REM Requires jbosssch.dll to be loaded in JVM
@if not ""%2"" == """" goto execSignal
echo Missing signal parameter.
echo Usage: service signal [0...9]
goto cmdEnd
:execSignal
jbosssvc.exe -k%2 %SVCNAME%
goto cmdEnd

:cmdEnd
