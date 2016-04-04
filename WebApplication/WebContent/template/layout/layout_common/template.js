        function Template_Function(){
            var that  = this;
            var TemplateLayout = Backbone.Marionette.Layout.extend({
//              template: "#_layout_typical",
              regions: {
                border1: "#border1",
                border2: "#border2",
                body: "#body",
                border3: "#border3",
              },
              
              render: function() {
//                var template = that.template;
//                var templateHtml = $("#_body_1").html();
//                var borderHtml = _.template(templateHtml, {parms:template.parms, Web2App:Web2App, });
//                return borderHtml;
            	  return "AAA";
              }

            });      
            var layout = new TemplateLayout();
//            layout.render();
            return layout;
        };



