<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.appcloud.vm.common.Constants"%>
<html>

<head>
    <title><s:property value="serverName"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
                Remove this if you use the .htaccess -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <!-- Apple iOS Safari settings -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <!-- App Start Icon  -->
    <link rel="apple-touch-startup-image" href="images/screen_320x460.png" />
    <!-- For iOS devices set the icon to use if user bookmarks app on their homescreen -->
    <link rel="apple-touch-icon" href="images/screen_57x57.png">
    <!--
    <link rel="apple-touch-icon-precomposed" href="images/screen_57x57.png" />
    -->


    <!-- Stylesheets -->
    <link rel="stylesheet" href="include/base.css" title="plain">

     <!--
    <script type='text/javascript'
        src='http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js'></script>
    -->
    <script src="include/util.js"></script>
    <%--<base href="<%=request.getScheme() + "://" + request.getServerName()--%>
					<%--+ ":" + request.getServerPort() + request.getContextPath()%>/" />--%>
</head>

<body style="margin: 0px;">
    <div id="noVNC_screen">
            <div id="noVNC_status_bar" class="noVNC_status_bar" style="margin-top: 0px;">
           		<input type=hidden id='host' value="<s:property value="host"/>" />
				<input type=hidden id='port' value="<s:property value="port"/>" />
            	<input type=hidden id='password' value="<s:property value="password"/>" />
                <table border=0 width="100%"><tr>
                    <td>
                        <input type=hidden id="noVNC_status" style="position: relative; height: auto;"/>
                    </td>
                    <td width="1%"><div id="noVNC_buttons">
                        <input type=button value="Send CtrlAltDel"
                            id="sendCtrlAltDelButton">
                        <span id="noVNC_xvp_buttons">
                        <input type=button value="Shutdown"
                            id="xvpShutdownButton">
                        <input type=button value="Reboot"
                            id="xvpRebootButton">
                        <input type=button value="Reset"
                            id="xvpResetButton">
                        </span>
                            </div></td>
                </tr></table>
            </div>
            <canvas id="noVNC_canvas" width="600px" height="480px">
            	
                Canvas not supported.
            </canvas>
        </div>
        <script>
        /*jslint white: false */
        /*global window, $, Util, RFB, */
        "use strict";

        // Load supporting scripts
        Util.load_scripts(["webutil.js", "base64.js", "websock.js", "des.js",
                           "keysymdef.js", "keyboard.js", "input.js", "display.js",
                           "jsunzip.js", "rfb.js"]);

        var rfb;

        function passwordRequired(rfb) {
            var msg;
            msg = '<form onsubmit="return setPassword();"';
            msg += '  style="margin-bottom: 0px">';
            msg += 'Password Required: ';
            msg += '<input type=password size=10 id="password_input" class="noVNC_status">';
            msg += '<\/form>';
            $D('noVNC_status_bar').setAttribute("class", "noVNC_status_warn");
            $D('noVNC_status').innerHTML = msg;
        }
        function setPassword() {
            rfb.sendPassword($D('password_input').value);
            return false;
        }
        function sendCtrlAltDel() {
            rfb.sendCtrlAltDel();
            return false;
        }
        function xvpShutdown() {
            rfb.xvpShutdown();
            return false;
        }
        function xvpReboot() {
            rfb.xvpReboot();
            return false;
        }
        function xvpReset() {
            rfb.xvpReset();
            return false;
        }
        function updateState(rfb, state, oldstate, msg) {
            var s, sb, cad, level;
            s = $D('noVNC_status');
            sb = $D('noVNC_status_bar');
            cad = $D('sendCtrlAltDelButton');
            switch (state) {
                case 'failed':       level = "error";  break;
                case 'fatal':        level = "error";  break;
                case 'normal':       level = "normal"; break;
                case 'disconnected': level = "normal"; break;
                case 'loaded':       level = "normal"; break;
                default:             level = "warn";   break;
            }

            if (state === "normal") {
                cad.disabled = false;
            } else {
                cad.disabled = true;
                xvpInit(0);
            }

            if (typeof(msg) !== 'undefined') {
                sb.setAttribute("class", "noVNC_status_" + level);
                s.innerHTML = msg;
            }
        }

        function xvpInit(ver) {
            var xvpbuttons;
            xvpbuttons = $D('noVNC_xvp_buttons');
            if (ver >= 1) {
                xvpbuttons.style.display = 'inline';
            } else {
                xvpbuttons.style.display = 'none';
            }
        }

        window.onscriptsload = function () {
            var host, port, password, path, token;

            $D('sendCtrlAltDelButton').style.display = "inline";
            $D('sendCtrlAltDelButton').onclick = sendCtrlAltDel;
            $D('xvpShutdownButton').onclick = xvpShutdown;
            $D('xvpRebootButton').onclick = xvpReboot;
            $D('xvpResetButton').onclick = xvpReset;
			
            //var title = "<s:property value='serverName'/>";
            WebUtil.init_logging(WebUtil.getQueryVar('logging', 'warn'));
            //document.title = unescape(WebUtil.getQueryVar('title', title));
            // By default, use the host and port of server that served this file
           // host = WebUtil.getQueryVar('host', window.location.hostname);
            //port = WebUtil.getQueryVar('port', window.location.port);
           
            host =document.getElementById("host").value;
            port =document.getElementById("port").value;

            // if port == 80 (or 443) then it won't be present and should be
            // set manually
            if (!port) {
                if (window.location.protocol.substring(0,5) == 'https') {
                    port = 443;
                }
                else if (window.location.protocol.substring(0,4) == 'http') {
                    port = 80;
                }
            }

            // If a token variable is passed in, set the parameter in a cookie.
            // This is used by nova-novncproxy.
            token = WebUtil.getQueryVar('token', null);
            if (token) {
                WebUtil.createCookie('token', token, 1)
            }

            //password = WebUtil.getQueryVar('password', '');
            password =document.getElementById("password").value;
            
            //path = WebUtil.getQueryVar('path', 'websockify');
            path = "websockify/?token="+WebUtil.getQueryVar('token', '');

            if ((!host) || (!port)) {
                updateState('failed',
                    "Must specify host and port in URL");
                return;
            }

            rfb = new RFB({'target':       $D('noVNC_canvas'),
                           'encrypt':      WebUtil.getQueryVar('encrypt',
                                    (window.location.protocol === "https:")),
                           'repeaterID':   WebUtil.getQueryVar('repeaterID', ''),
                           'true_color':   WebUtil.getQueryVar('true_color', true),
                           'local_cursor': WebUtil.getQueryVar('cursor', true),
                           'shared':       WebUtil.getQueryVar('shared', true),
                           'view_only':    WebUtil.getQueryVar('view_only', false),
                           'updateState':  updateState,
                           'onXvpInit':    xvpInit,
                           'onPasswordRequired':  passwordRequired});
            rfb.connect(host, port, password, path);
        };
        </script>

    </body>
</html>
