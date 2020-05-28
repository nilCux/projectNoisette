
async function login() {
    let mail = document.getElementById('user_email').value
    let passwd = document.getElementById('user_password').value
    if (mail === "" || passwd === "") {
        alert("All these blanks must be filled")
    } else {
        if(checkMail(mail)) {
            passwd = md5(passwd)
            await axios.post("/login/submit/",{
                mail: mail,
                password: passwd
            })
            location.assign("/login/submit/")
            return true
        } else {
            alert("Please offer a validate mail.")
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