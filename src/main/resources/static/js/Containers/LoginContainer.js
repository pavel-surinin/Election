var LoginContainer = React.createClass({
  getInitialState: function() {
    return {
      username : '',
      password : '',
      wrongAuth : false,
      isLogged : false,
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
    .get('users/logged')
    .then(function(response){
      console.log(response.data);
      if (response.data == 'none') {
        self.setState({isLogged : false});
      } else {
        self.setState({isLogged : true});
      }
    })
    .catch(function(err){
      console.error('LoginContainer.componentWillMount.axios',err);
    });
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    var self = this;
    console.log(this.state);
    axios
    .post('users/login', {
      username : self.state.username,
      password : self.state.password,
    })
    .then(function(response){
      if (response.data) {
        self.context.router.push('/county');
      } else {
        self.setState({wrongAuth : true});
      }
      console.log(response.data);

    })
    .catch(function(err){
      console.error('axios error at LoginContainer', err);
    });
  },
  checkWhoIsLogged: function() {
    var self = this;
    axios
    .get('users/logged')
    .then(function(response){
      console.log(response.data);
      return response.data;
    })
    .catch(function(err){
      console.error('axios error at LoginContainer', err);
    });
  },
  onHandleUsernameChange : function(event){
    this.setState({username: event.target.value});
  },
  onHandlePasswordChange : function(event){
    this.setState({password: event.target.value});
  },
  onHandleLogoutClick : function(){
    var self = this;
    axios
    .get('/users/logout')
    .then(function(response) {
      self.setState({isLogged : false});
    })
    .catch(function(err){
      console.error('LoginContainer.onHandleLogoutClick.axios',err);
    });
  },
  render: function() {
    return (
      <LoginComponent
      onHandleSubmit={this.onHandleSubmit}
      onHandlePasswordChange={this.onHandlePasswordChange}
      onHandleUsernameChange={this.onHandleUsernameChange}
      userLogged={this.checkWhoIsLogged}
      wrongAuth={this.state.wrongAuth}
      isLogged={this.state.isLogged}
      onHandleLogoutClick={this.onHandleLogoutClick}
      />
    );
  }

});

LoginContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.LoginContainer = LoginContainer;
