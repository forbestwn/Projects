
requirejs.config({
    baseUrl: '',
    paths: {
        libs: 'libs',
        text: 'libs/text',
        backbone: 'libs/backbone',
        underscore: 'libs/underscore',
        jquery: 'libs/jquery',
        'jquery.mobile': 'libs/jquery.mobile',
        'domReady': 'libs/domReady',
    },
    
    shim: {
        'jquery.mobile':{
            deps: ['jquery'],
        },
        
        'underscore': {
            exports: '_'
        },    
        
        'jquery':{
            deps: ['underscore'],
            exports: '$',
        },
        
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
    }
});

requirejs(['jquery', 'backbone', 'domReady'],
    function   ($, Backbone,   domReady) {
        //This function is called once the DOM is ready.
        //It will be safe to query the DOM and manipulate
        //DOM nodes in this function.
    }
        
);
