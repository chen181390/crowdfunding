$(function () {
    let sendBtn = $("#sendBtn");
    sendBtn.click(function () {
        let wholeTime = 10;
        sendBtn.prop("disabled", true);
        sendBtn.text("已发送 " + wholeTime + "s");
        let intervalId = setInterval(function () {
            if (wholeTime <= 0) {
                clearInterval(intervalId);
                sendBtn.text("重新获取验证码");
                sendBtn.prop("disabled", false);
                return;
            }
            sendBtn.text("已发送 " + --wholeTime + "s");
        }, 1000);
        let phoneNum = $("[name=phoneNum]").val().trim();
        $.ajax({
            url: "auth/member/send/short/message.json",
            type: "post",
            data: {
                phoneNum: phoneNum,
            },
            dataType: "json",
            success: function (response) {
                let result = response.result;
                if (result != "SUCCESS") {
                    layer.msg("发送失败！请再试一次！");
                    return;
                }
                layer.msg("发送成功");
            },
            error: function (response) {
                layer.msg(response.status + " " + response.statusText);
            }
        });
    });
});