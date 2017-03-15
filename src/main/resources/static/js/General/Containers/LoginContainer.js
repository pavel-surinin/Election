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
    .post('/login/check')
    .then(function(response){
      if (response.data == 'admin' || response.data == 'representative') {
        self.setState({isLogged : true});
      } else {
        self.setState({isLogged : false});
      }
    })
    .catch(function(err){
      console.error('LoginContainer.componentWillMount.axios',err);
    });
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    var self = this;
    var data = new FormData();
    var header = { headers: {
      'Content-Type': 'multipart/form-data'}};
    data.append('username', self.state.username);
    data.append('password', self.state.password);
    axios
    .post('/login', data, header)
    .then(function(response){
      if (response.data == 'none') {
        self.setState({wrongAuth : true});
      } else {
        if (response.data == 'admin') {
          self.context.router.push('/admin/');
        } else {
          self.context.router.push('/representative/');
        }
      }
    })
    .catch(function(error){
      if (error.response) {
        if (error.response.status == 401) {self.setState({wrongAuth : true});}
      }
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
    .get('/logout')
    .then(function(response) {
      self.setState({isLogged : false});
    })
    .catch(function(err){
      console.error('LoginContainer.onHandleLogoutClick.axios',err);
    });
  },
  render: function() {
    document.title='Login Rinkimai 2017';
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
