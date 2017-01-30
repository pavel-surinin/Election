function getCounty(self) {
  axios
    .get('/county')
    .then(function(response){
      self.setState({
        countyList :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('CountyContainer.getCounty.axios.get.county', err);
    });
}

var CountyContainer = React.createClass({
  getInitialState: function() {
    return {
      countyList : [],
      isLoading : true,
      succesCreateText : '',
    };
  },
  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    getCounty (this);
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

  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log(this.state.countyList);
      return (
        <CountyListViewComponent
          countyList={this.state.countyList}
          onHandleDelete={this.onHandleDelete}
          succesCreateText={this.state.succesCreateText}
        />
      );
    }
  }
});

window.CountyContainer = CountyContainer;
