
async function register() {
    let mail = document.getElementById('user_email').value
    let passwd = document.getElementById('user_password').value
    let vPasswd = document.getElementById('user_password_verify').value
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
                await axios.post("/register/submit/",{
                    mail: mail,
                    password: passwd
                }).then(
                ()=>location.assign("/register/submit/")
                )
                return true
            } else {
                alert("Please ofer a validate mail.")
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