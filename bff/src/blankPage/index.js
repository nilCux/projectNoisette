const koa = require('koa');
const axios = require('axios')
const fs = require('fs');
const mount = require('koa-mount');
const static = require('koa-static');
const ConfigurationManager = require('./ConfigurationManager')


const app = new koa();
const blankPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');
var blankResult = `<html><meta http-equiv="refresh" content="0.5">Logging in...</html>`
var tokenValue = `vide`


app.use(mount('/favicon.ico',ctx => {
  // ignore favicon
  ctx.state = 200
}));

app.use(
    static(__dirname + '/source/')
);

app.use(
    mount('/status/', async (ctx) => {
        //console.log(ctx.query)
        //console.log(ctx.params)
        //console.log(ctx.cookies.get('AUTH_TOKEN'))
        //console.log(ctx.request.headers)
        var axiosConfig = {
            headers:{Cookie: ConfigurationManager.defaultCookieInstanceGenerator(ctx)} 
        }
        await axios.get(ConfigurationManager.Constants.blankService, axiosConfig)
                .then((res)=>{
                    console.log(res)
                    blankResult = res.data
                })
                .catch((e)=>{});
        

        ctx.cookies.set('Token', tokenValue)
        ctx.response.body = blankResult
        ctx.status = 200
    })
);

app.use(
    mount('/', async (ctx) => {
        ctx.body = blankPageContent
    })
);

module.exports = app;