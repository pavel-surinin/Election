var DistrictEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      adress : '',
      county : 1,
      countyList : [],
      adressErrorMesages : [],
      nameErrorMesages : [],
      existsErrorMesages : [],
      isLoading: true,
    };
  },
  componentWillMount: function() {
      var self = this;
      axios
        .get('/county')
        .then(function(response){
          self.setState({
            countyList: response.data,
          });
        })
        .catch(function(err){
          console.error('DistrictEditContainer.componentWillMount.axios.getCouty', err);
        });
      axios
      .get('/district/' + this.props.params.id)
      .then(function(response){
        self.setState({
          name: response.data.name,
          adress: response.data.adress,
          county: response.data.countyId,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('DistrictEditContainer.componentWillMount.axios.getDistrict', err);
      });
    },
    onHandleNameChange : function(event){
        this.setState({name : event.target.value});
      },
    onHandleAdressChange : function(event){
        this.setState({adress : event.target.value});
      },
    onHandleCountyChange : function(event){
        this.setState({county : event.target.value});
      },
    onHandleSubmit : function(event){
        event.preventDefault();
        var countyId = {id : this.state.county};
        var postRequest = {
          id: this.props.params.id,
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
            self.context.router.push('/admin/district?succesCreateText=Apylinkė ' + self.state.name + ' atnaujinta');
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

  render: function() {
      if (this.state.isLoading) {
        return (
          <div>
            <img src='./Images/loading.gif'/>
          </div>
        );
      } else {
        return (
          <DistrictCreateEditComponent
          onHandleSubmit={this.onHandleSubmit}
          onHandleNameChange={this.onHandleNameChange}
          onHandleCountyChange={this.onHandleCountyChange}
          onHandleAdressChange={this.onHandleAdressChange}
          id={this.state.id}
          name= {this.state.name}
          adress= {this.state.adress}
          county= {this.state.county}
          countyList={this.state.countyList}
          action='Atnaujinti'
          />
      );
    }
  }
});
DistrictEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.DistrictEditContainer = DistrictEditContainer;
