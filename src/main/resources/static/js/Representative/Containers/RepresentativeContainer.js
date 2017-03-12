var RepresentativeContainer = React.createClass({
  getInitialState: function() {
    return {
      isLogged  : false,
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
    .post('/login/check')
    .then(function(response) {
      if (response.data == 'representative') {
        self.setState({isLogged : true});
      } else {
        self.setState({isLogged : false});
        self.context.router.push('/login');
      }
    })
    .catch(function(err){
      console.error('AdminComponent.onHandleLogout.axios',err);
    });

  },
  render: function() {
    if (this.state.isLogged) {
      return (
        <RepresentativeComponent>
        {this.props.children}
        </RepresentativeComponent>
      );
    }
    return null;
  }

});

RepresentativeContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.RepresentativeContainer = RepresentativeContainer;
