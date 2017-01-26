var DistrictRepresentativeCreateContainer = React.createClass({
  onHandleSubmit : function(event){
    event.preventDefault();
    var districtId = {id : this.state.district};
    var postRequest = {
      name : this.state.name,
      surname : this.state.surname,
      district : districtId,
      surnameErrorMesages : [],
      nameErrorMesages : [],
    };
    //validation Surname
    var surnameErrorMesages = [];
    var nameErrorMesages = [];
    if(!validation.checkSurname(this.state.surname)) {surnameErrorMesages.push('Pavardė gali atrodyti: Pavardenis arba Pavardenis-Kavardenis');}
    if(!validation.checkMax(this.state.surname,50)) {surnameErrorMesages.push('Pavardė negali būti ilgesne, nei 50 simbolių');}
    if(!validation.checkMin(this.state.surname,3)) {surnameErrorMesages.push('Pavardė negali būti trumpesnė, nei 3 simboliai');}
    //validation Name
    if(!validation.checkName(this.state.name)) {nameErrorMesages.push('Vardas gali tureti tik raides');}
    if(!validation.checkMax(this.state.name,20)) {nameErrorMesages.push('Vardas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,3)) {nameErrorMesages.push('Vardas negali būti trumpesnis, nei 3 simboliai');}
    if (surnameErrorMesages.length == 0 && nameErrorMesages.length == 0) {
      var self = this;
      axios
      .post('/representative', postRequest)
      .then(function(response){
        self.context.router.push('/admin/representative');
      })
      .catch(function(err){
        console.error('DistrictRepresentativeCreateContainer.onHandleSubmit.axios', err);
      });
    } else {
      this.setState({
        surnameErrorMesages : surnameErrorMesages,
        nameErrorMesages : nameErrorMesages,
      });
    }

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
      .get('/district/nonerepresentatives')
      .then(function(response){
        console.log(response.data);
        if (response.data.length == 0) {
          var a = '';
        } else {
          self.setState({
            districtList : response.data,
            district : response.data[0].id,
          });
        }
      })
      .catch(function(error){
        console.error('DistrictRepresentativeCreateContainer.componentWillMount.axios', error);
      });
  },
  onHandleDistrictChange : function(event){
    var districtId = parseInt(event.target.value);
    this.setState({district : districtId});
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
    console.log('Check regex - ', validation.checkName(event.target.value));
    console.log('Check max - ', validation.checkMax(event.target.value, 50));
    console.log('Check min - ', validation.checkMin(event.target.value, 3));
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
       nameErrorMesages={this.state.nameErrorMesages}
       surnameErrorMesages={this.state.surnameErrorMesages}
       action='Sukurti'
       />
    );
  }

});

DistrictRepresentativeCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.DistrictRepresentativeCreateContainer = DistrictRepresentativeCreateContainer;
