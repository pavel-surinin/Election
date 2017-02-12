var fileErrorMesages = [];
function getCounty(self) {
  axios
    .get('/county')
    .then(function(response){
      self.setState({
        countyList :  response.data,
        isLoading : false,
      });
      EventEmitter.publish({ eventType: 'LogCounty' });
    })
    .catch(function(err){
      console.error('CountyContainer.getCounty.axios.get.county', err);
    });
}

function validate(self, code){
  console.log(self);
  if (code == 415) {fileErrorMesages.push('Netinkamas CSV failas, stulpelių skaičius skiriasi');}
  if (code == 422) {fileErrorMesages.push('Blogi CSV duomenys');}
  if (code == 424) {fileErrorMesages.push('Blogi CSV duomenys, yra kandidatu, kurie jau užregistruoti');}
  if (code == 451) {fileErrorMesages.push('Kandidatas priskiriamas partijai, kuri neregistruota');}
  self.setState({
    fileErrorMesages : fileErrorMesages,
  });
}

var CountyContainer = React.createClass({
  getInitialState: function() {
    return {
      countyList : [],
      isLoading : true,
      succesCreateText : '',
      countyId : null,
      file : null,
      fileErrorMesages : [],
      succesMessage : '',
    };
  },
  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    getCounty(this);
    this.setState({fileErrorMesages : []});
  },

  onHandleFormAddSingleCandSubmit : function(event){
    event.preventDefault();
    this.setState({fileErrorMesages : [], succesMessage : ''});
    fileErrorMesages = [];
    var self = this;
    //uploadfile constructing data
    var file = 'nofile.aaa';
    if (this.state.file != null) {
      file = this.state.file;}
    var data = new FormData();
    var header = { headers: {
      'Content-Type': 'multipart/form-data',
      'County-Id': this.state.countyId} };
    data.append('file', file);
    //validation
    console.log(file);
    if(!validation.checkForCsv(file.name)) {fileErrorMesages.push('Būtina prisegti *.csv formato failą');}
    if (fileErrorMesages.length == 0) {
      axios
      .put('/county', data, header)
      .then(function(response){
        getCounty(self);
        self.setState({succesMessage : 'Apygardai sėkmingai įkeltas vienmandaties apygardos sąrašas'});
      })
      .catch(function (error) {
        if (error.response) {
          validate(self, error.response.status);
        }
      });
    }
    else {
      this.setState({fileErrorMesages : fileErrorMesages});
    }
  },
  onHandleDelete: function(i) {
    var self = this;
    event.preventDefault();
    axios
      .delete('/county/'+ i)
      .then(function(response){
        getCounty(self);
      })
      .catch(function(err){
        console.error('CountyContainer.onHandleDelete.axios', err);
      });
  },
  onFileChange : function(file, id){
    this.setState({
      countyId : id,
      file : file,
    });
  },
  onHandleAddClick : function(){
    this.setState({fileErrorMesages : []});
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
        <CountyListViewComponent
          countyList={this.state.countyList}
          onHandleDelete={this.onHandleDelete}
          succesCreateText={this.state.succesCreateText}
          onFileChange={this.onFileChange}
          fileErrorMesages={this.state.fileErrorMesages}
          onHandleFormAddSingleCandSubmit={this.onHandleFormAddSingleCandSubmit}
          onHandleAddClick={this.onHandleAddClick}
          succesMessage={this.state.succesMessage}
        />
      );
    }
  }
});

CountyContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.CountyContainer = CountyContainer;
