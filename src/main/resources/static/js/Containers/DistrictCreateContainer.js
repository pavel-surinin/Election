var DistrictCreateContainer = React.createClass({
  onHandleSubmit : function(event){
    event.preventDefault();
    var countyId = {id : this.state.county};
    var postRequest = {
      name : this.state.name.trim(),
      adress : this.state.adress.trim(),
      county : countyId,
    };
    //validation
    var nameErrorMesages = [];
    if(!validation.checkMax(this.state.name,35)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 35 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    var adressErrorMesages = [];
    if(!validation.checkMax(this.state.adress,50)) {adressErrorMesages.push('Adresas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.adress,4)) {adressErrorMesages.push('Adresas negali būti trumpesnis, nei 4 simboliai');}
    if (nameErrorMesages.length == 0 && adressErrorMesages.length == 0) {
      var self = this;
      axios
      .post('/district', postRequest)
      .then(function(response){
        console.log(response);
        self.context.router.push('/admin/district?succesCreateText=Apylinkė ' + self.state.name + ' sukurta');
      })
      .catch(function(err){
        var existsErrorMesages = [];
        existsErrorMesages.push('Apylinkė su pavadinimu ' + self.state.name + ' jau priskirta šitai apygardai');
        self.setState({existsErrorMesages : existsErrorMesages});
        // console.error('DistrictCreateContainer.onHandleSubmit.axios', err);
      });
    } else {
      this.setState({
        nameErrorMesages : nameErrorMesages,
        adressErrorMesages : adressErrorMesages,
      });
    }
  },

  getInitialState: function() {
    return {
      name : '',
      adress : '',
      county : 1,
      countyList : [],
      adressErrorMesages : [],
      nameErrorMesages : [],
      existsErrorMesages : [],
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/county')
      .then(function(response){
        self.setState({
          countyList : response.data,
          county : response.data[0].id,
        });
      })
      .catch(function(error){

        console.log('lol', error.response);
        console.error('DistrictCreateContainer.componentWillMount.axios', error.response.status);
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
       nameErrorMesages={this.state.nameErrorMesages}
       adressErrorMesages={this.state.adressErrorMesages}
       existsErrorMesages={this.state.existsErrorMesages}
       action='Sukurti'
       />
    );
  }

});

DistrictCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.DistrictCreateContainer = DistrictCreateContainer;
