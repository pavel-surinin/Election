var CountyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
    };
  },

  onHandleNameChange : function(event){
    this.setState({name: event.target.value});
  },

  onHandleSubmit: function(event){
    var self = this;
    event.preventDefault();
    axios.post('/county', this.state)
    .then(function(response){
      console.log(response);
      self.context.router.push('/county');
    })
    .catch(function(err){
      console.log('CountyCreateContainer.onHandleSubmit - ',err);
    });
  },

  onHandleCancel:function(){
    this.context.router.push('/county');
  },

  render: function() {
    return (
      <div>
      <CountyCreateEditFormComponent
          submitButtonName='Registruoti'
          name={this.state.name}
          onHandleNameChange={this.onHandleNameChange}
          onHandleSubmit={this.onHandleSubmit}
          onHandleCancel={this.onHandleCancel}

        />
      </div>
    );
  }
  });

  CountyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
  };

window.CountyCreateContainer = CountyCreateContainer ;
