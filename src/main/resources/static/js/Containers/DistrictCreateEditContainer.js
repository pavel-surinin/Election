var DistrictCreateEditContainer = React.createClass({
  getInitialState: function() {
    return {
      title : '',
      county: '',
      representatives: '',
    };
  },

  onHandleTitleChange : function(event){
    this.setState({title: event.target.value});
  },

  onHandleCountyChange : function(event){
    this.setState({county: event.target.value});
  },

  onHandleRepresentativesChange : function(event){
    this.setState({representative : event.target.value});
  },

  onHandleSubmit: function(event){
    var self = this;
    event.preventDefault();
    axios.post('/api/districts', this.state)
    .then(function(response){
      console.log(response);
      self.context.router.push('/');
    })
    .catch(function(err){
      console.log('DistrictCreateEditContainer.onHandleSubmit - ',err);
    });

  },

  onHandleCancel:function(){
    this.context.router.push('/');
  },

  render: function() {
    return (
      <div>
      <DistrictCreateEditComponent
          submitButtonName='Išsaugoti'

          title={this.state.title}
          county={this.state.county}
          representatives={this.state.representatives}

          onHandleTitleChange={this.onHandleTitleChange}
          onHandleCountyChange={this.onHandleCountyChange}
          onHandleRepresentativesChange={this.onHandleRepresentativesChange}

          onHandleSubmit={this.onHandleSubmit}
          onHandleCancel={this.onHandleCancel}

        />
      </div>
    );
  }
});

DistrictCreateEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.DistrictCreateEditContainer = DistrictCreateEditContainer;
