const koa = require('koa');
const fs = require('fs');
const mount = require('koa-mount');

const app = new koa();
const loginPageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');

app.use(
    mount('/', async (ctx) => {
        ctx.body = loginPageContent
    })
);

module.exports = app;