var LoginContainer = React.createClass({
  getInitialState: function() {
    return {
      username : '',
      password : '',
    };
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    var self = this;
    console.log(this.state);
    axios
    .post('users/login', self.state)
    .then(function(response){
      window.App.call;
      self.context.router.push('/county');
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

  render: function() {
    return (
      <LoginComponent
      onHandleSubmit={this.onHandleSubmit}
      onHandlePasswordChange={this.onHandlePasswordChange}
      onHandleUsernameChange={this.onHandleUsernameChange}
      userLogged={this.checkWhoIsLogged}
      />
    );
  }

});

LoginContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.LoginContainer = LoginContainer;
