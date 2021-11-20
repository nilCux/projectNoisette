/**
 * This page shall not be a static page.
 * It should be a fillable template.
 */


const koa = require('koa');
const fs = require('fs');
const mount = require('koa-mount');

const app = new koa();
const homePageContent = fs.readFileSync(__dirname + '/index.html', 'utf-8');

app.use(
    mount('/', async (ctx) => {
        ctx.body = homePageContent
    })
);

module.exports = app;
