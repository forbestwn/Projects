        var Template_Function = function(template){
            var that  = this;
            var TemplateView = Marionette.ItemView.extend({
              className: "document-row",
//              template: "#_body_1",
              events: {
                  "click a" : "clickBtn"
//                "click .icon":          "open",
//                "click .button.edit":   "openEditDialog",
//                "click .button.delete": "destroy"
              },

              clickBtn : function(){
                  this.trigger('clickBtn');
              },

              initialize: function() {
//                this.listenTo(this.model, "change", this.render);
              },

              render: function() {
//                var template = that.template;
//                var templateHtml = $("#_body_1").html();
//                var borderHtml = _.template(templateHtml, {parms:template.parms, Web2App:Web2App, });
//                this.$el.html(borderHtml);
            	  return this.$el;
              }

            });      
            var view = new TemplateView();
//            view.render();
            return view;
        };
