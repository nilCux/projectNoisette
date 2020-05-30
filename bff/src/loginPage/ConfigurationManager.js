function defaultCookieInstanceGenerator(ctx){
    if (ctx == null) {
        log.info("Context empty")
        return null;
    }
    return `userID=${ctx.cookies.get('userID')}; AUTH_TOKEN=${ctx.cookies.get('AUTH_TOKEN')}; JSESSIONID=${ctx.cookies.get('JSESSIONID')}`
}

const Constants = {
    key : 'value',
    tokenNameInCookie : 'AUTH_TOKEN',
    idNameInCookie: 'userID'
}

module.exports = {defaultCookieInstanceGenerator, Constants}