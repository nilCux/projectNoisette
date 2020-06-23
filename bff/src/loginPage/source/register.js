
async function register() {
    let mail = document.getElementById('reg_email').value
    let passwd = document.getElementById('reg_password').value
    let vPasswd = document.getElementById('reg_password_confirm').value
    let fakeName = document.getElementById('reg_fn').value
    if (mail === "" || passwd === "" || vPasswd==="") {
        alert("All these blanks must be filled")
    } else {
        if (vPasswd.localeCompare(passwd) != 0) {
            alert("您两次输入的密码不匹配！")
        //return false
        } else {
            if(checkMail(mail)) {
                passwd = md5(passwd)
                vPasswd=""
                await axios.post("/login/registry/",{
                    fakeName: fakeName,
                    mail: mail,
                    password: passwd
                }).then(
                    (res)=>{
                        if (res.status==200)
                            alert("注册成功")
                        else alert("我的锅，没注册成功。再试一次吧！")
                    }
                )
                .catch((err)=>{
                    let status = err.response.status

                    if (status == 304)
                        alert("该邮箱已被注册")
                    else
                        alert("服务器有点忙，回头再试试吧")
                })
                .finally(()=>{location.assign("/login/")})
                return true
            } else {
                alert("请提供有效注册邮箱")
            }
        }   
    }
    return false
}

function checkMail(mail){
    　　const reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/
    　　if ((mail === "") || (!reg.test(mail))) {
    　　　　return false;
    　　}else{
    　　　　return true;
    　　}
}