const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');
const ConfigurationManager = require('./ConfigurationManager')
const bodyParser = require('koa-bodyparser');



const app = new koa();
const loginPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var loginResultPageContent = ""//`<html><meta http-equiv="refresh" content="0.5">Logging in...</html>`

app.use(bodyParser());


app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/submit/', async (ctx) => {
        var axiosConfig = {headers:{
            timeout: 3000,
            Cookie: ConfigurationManager.defaultCookieInstanceGenerator(ctx)
        } }
        if(ctx.method === 'POST') {
            dataBody = ctx.request.body
            console.log(dataBody)
            await axios.post('http://127.0.0.1:8080/login', dataBody, axiosConfig)
                .then((res)=>{
                    resultCode = res.data.code
                    if (resultCode === 200) {
                        let tokenValue = res.data.data.token
                        let userId = res.data.data.id
                        console.log(res.data)
                        ctx.cookies.set(ConfigurationManager.Constants.tokenNameInCookie, tokenValue)
                        ctx.cookies.set(ConfigurationManager.Constants.idNameInCookie, userId)
                    } else {
                        console.log(res.data)
                    }
                    
                })
                .catch((reason) => {
                    console.log(reason)
                })
                .finally(()=>{
                    console.log("Login: " + resultCode)
                    
            })
        } else {
            await axios.get('http://127.0.0.1:8080/blank/', axiosConfig)
                .then((res)=>{
                    resultCode = res.data.code
                    if (resultCode === 200) {
                        loginResultPageContent = `<html>Status: Logged in</html>`
                        console.log(res.data)
                    } else {
                        console.log(res.data)
                        loginResultPageContent = `<html>Status: Not logged in</html>`
                    }   
                })
                .catch((reason) => {
                    resultCode = reason.response.status
                    console.log(reason.response.data)
                    loginResultPageContent = `<html>${reason.response.data.message}</html>`
                })
                .finally(()=>{
                    console.log("Logged in status maintaining: " + resultCode)
                    ctx.body = loginResultPageContent
                })
        }    
        ctx.status= 200
                
    })
    
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = loginPageContent
    })
);

module.exports = app;