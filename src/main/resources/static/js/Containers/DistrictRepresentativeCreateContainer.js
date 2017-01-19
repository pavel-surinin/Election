var DistrictRepresentativeCreateContainer = React.createClass({
  onHandleSubmit : function(event){
    event.preventDefault();
    var districtId = {id : this.state.district};
    var postRequest = {
      name : this.state.name,
      surname : this.state.surname,
      district : districtId,
    };
    console.log(postRequest);
    var self = this;
    axios
      .post('/representative', postRequest)
      .then(function(response){
        console.log(response);
        self.context.router.push('/representative');
      })
      .catch(function(err){
        console.error('DistrictRepresentativeCreateContainer.onHandleSubmit.axios', err);
      });

  },

  getInitialState: function() {
    return {
      name : '',
      surname : '',
      district : 1,
      districtList : [],
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/district')
      .then(function(response){
        self.setState({districtList : response.data});
      })
      .catch(function(error){
        console.error('DistrictRepresentativeCreateContainer.componentWillMount.axios', error);
      });
  },
  onHandleDistrictChange : function(event){
    var districtId = parseInt(event.target.value);
    this.setState({district : districtId});
    console.log(this.state);
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSurnameChange : function(event){
    this.setState({surname : event.target.value});
  },

  render: function() {
    return (
      <DistrictRepresentativeCreateFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSurnameChange={this.onHandleSurnameChange}
       adress={this.state.adress}
       onHandleDistrictChange={this.onHandleDistrictChange}
       district={this.state.district}
       onHandleSubmit={this.onHandleSubmit}
       districtList={this.state.districtList}

       action='Sukurti'
       />
    );
  }

});

DistrictRepresentativeCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.DistrictRepresentativeCreateContainer = DistrictRepresentativeCreateContainer;
