const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');
const ConfigurationManager = require('./ConfigurationManager')


const app = new koa();
const loginPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var loginResultPageContent = `<html><meta http-equiv="refresh" content="0.5">Logging in...</html>`
var tokenValue = `vide`
var userId = 'none'



app.use(mount('/favicon.ico',ctx => {
  // ignore favicon
  ctx.state = 200
}));

app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/submit/', async (ctx) => {
        var axiosConfig = {headers:{
            Cookie: ConfigurationManager.defaultCookieInstanceGenerator(ctx)
        } }
        ctx.request.req.on('data', async function(data){ 
            dataBody = JSON.parse(data.toString())
            await axios.post('http://127.0.0.1:8080/login', dataBody, axiosConfig)
                .then((res)=>{
                    //console.log(res)
                    resultCode = res.data.code
                    if (resultCode === 200) {
                        tokenValue = res.data.data.token
                        userId = res.data.data.id
                        console.log("app level: " + ctx.cookies.get("userID"))
                        loginResultPageContent = `<html>Loged in successfully</html>`
                    } else {
                        loginResultPageContent = `<html>Failed loggingin</html>`
                    }
                    console.log(res.data)
                    console.log("Login: " + resultCode)
                })
                .catch((reason) => {console.log(reason)});    
        })
        ctx.cookies.set(ConfigurationManager.Constants.tokenNameInCookie, tokenValue)
        ctx.cookies.set(ConfigurationManager.Constants.idNameInCookie, userId)
        ctx.response.body = loginResultPageContent
        ctx.status = 200
    })
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = loginPageContent
    })
);

module.exports = app;