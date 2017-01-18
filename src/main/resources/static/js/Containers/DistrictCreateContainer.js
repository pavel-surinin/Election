var DistrictCreateContainer = React.createClass({
  onHandleSubmit : function(event){
    event.preventDefault();
    var countyId = {id : this.state.county};
    var postRequest = {
      name : this.state.name,
      adress : this.state.adress,
      county : countyId,
    };
    console.log(postRequest);
    var self = this;
    axios
      .post('/district', postRequest)
      .then(function(response){
        console.log(response);
        self.context.router.push('/district');
      })
      .catch(function(err){
        console.error('DistrictCreateContainer.onHandleSubmit.axios', err);
      });

  },

  getInitialState: function() {
    return {
      name : '',
      adress : '',
      county : 1,
      countyList : [],
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/county')
      .then(function(response){
        self.setState({countyList : response.data});
      })
      .catch(function(error){
        console.error('DistrictCreateContainer.componentWillMount.axios', error);
      });
  },
  onHandleCountyChange : function(event){
    var countyId = parseInt(event.target.value);
    this.setState({county : countyId});
    console.log(this.state);
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleAdressChange : function(event){
    this.setState({adress : event.target.value});
  },

  render: function() {
    return (
      <DistrictCreateEditComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleAdressChange={this.onHandleAdressChange}
       adress={this.state.adress}
       onHandleCountyChange={this.onHandleCountyChange}
       county={this.state.county}
       onHandleSubmit={this.onHandleSubmit}
       countyList={this.state.countyList}

       action='Sukurti'
       />
    );
  }

});

DistrictCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.DistrictCreateContainer = DistrictCreateContainer;
