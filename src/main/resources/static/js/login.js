/*!
 * qcl
 */
"use strict";
$(function () {
    //点击登录按钮
    $("#loginBtn").click(function () {
        login();
        console.log("点击了登录")
    });

    //管理登录
    function login() {
        let phone = $(" input[ name='username' ] ").val();
        let psw = $(" input[ name='password' ] ").val();
        if (phone === undefined || phone === "") {
            alert("请输入手机号");
            return;
        }
        if (psw === undefined || psw === "") {
            alert("请输入密码");
            return;
        }
        $.ajax({
            url: "/sell/admin/loginAdmin",
            data: {
                "phone": phone,
                "password": psw
            },
            success: function (data) {
                $("#mainContainer").html(data);
                console.log("登录成功", data);
                window.location.href = "/sell/seller/order/list";
            },
            error: function (err) {
                console.log("登录失败,用户名或者密码不正确", err);
                alert("登录失败,手机号或者密码不正确");
                toastr.error("error!");
            }
        });
    }
});