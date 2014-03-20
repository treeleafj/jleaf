Ext.define('jleaf.shop.LoginPanel',{
	extend : 'Ext.form.Panel',
	
	//登录触发
	handler : function(){},
	
	config : {
    	modal : true,
    	style : 'text-align: center;',
		hideOnMaskTap : true,
		showAnimation : {
			type : 'flip',
			duration : 400,
			easing : 'ease-out'
		},
		hideAnimation :{
			type : 'popOut',
			duration : 350,
			easing : 'ease-out'
		},
		
		centered : true,
		width : '80%',
		height : '60%',
		minHeight : 300,
		styleHtmlContent : true,
		scrollable : true
	},
	
	createForm : function(){
		return [{
				xtype : 'titlebar',
   				docked : 'top',
   				title : '用户登录'
   			},{
   				xtype: 'fieldset',
                margin: 10,
                instructions : 'shop login',
                items: [{
	       				xtype : 'textfield',
	       				label : '账户:',
	       				name : 'username'
	                },{
	                	xtype : 'passwordfield',
	                	label : '密码:',
	                	name : 'password'
	                },
                    {
                    	xtype : 'button',
	       				margin: '20 auto',
	       				maxWidth : 200,
                    	text : 'login',
                    	scope : this,
                    	handler : function(btn){
                    		
                    		var form = this;	
                    		
                    		var username = form.getValues().username;
                    		var password = form.getValues().password;
                    		
                    		if(!username || username.trim().length <= 0){
                    			Ext.Msg.alert('错误','请输入账户');
                    			return;
                    		}
                    		if(!password || password.trim().length <= 0){
                    			Ext.Msg.alert('错误','请输入密码');
                    			return;
                    		}
                    		form.submit({
                    			url : 'auth',
                    			method : 'post',
                    			scope : this,
                    			params : {
                    				_method : 'put'
                    			},
                    			waitMsg : {
                    				xtype: 'loadmask',
									message: '登录中...'
                    			},
                    			success : function(form,data){
                    				if(data){
                    					this.fireEvent('loginSuccess',this,data);
                    				}
                    				form.hide();
                    			},
                    			failure : function(form,result){
                    				if(result.msg){
	                					Ext.Msg.alert('登录错误',result.msg);
                    				}else{
	                					Ext.Msg.alert('登录错误',"网络异常,请稍后尝试");
                    				}
                    			}
                    		});
                    	}
                	}
            	]
			}
		];
	},
	
	
	//构造函数
	constructor: function (config) {
		if(!config){
			config = {};
		}
		
		config.scope = this;
		config.items = this.createForm();
		
		this.on('loginSuccess',this.handler,this);
		
		this.callParent([config]);
    }
});